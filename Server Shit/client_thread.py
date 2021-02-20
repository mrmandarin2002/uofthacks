import socket, threading, struct
from datetime import datetime
import random

PORT = 5050
FORMAT = 'utf-8'
DISCONNECT_MESSAGE = "!DISCONNECT"
HEADER = 16

# function to get the current time
def get_time():
    return str(datetime.now()).split()[1].split(".")[0]+" "

class client_thread():

    def __init__ (self, conn, addr, controller):
        self.conn = conn
        self.addr = addr
        self.client_name = ""
        self.controller = controller
        self.functions = {

            "fucker" : self.fucker,
            "check_user" : self.check_user,
            "sign_up" : self.sign_up,
            "get_community" : self.get_community,
            "create_community" : self.create_community,
        
            #add all the methods within this class

        }
        threading.Thread(target = self.handle_client, args = (conn, addr)).start()

    def handle_client(self, conn, addr):
        print(get_time() + f"Client with address {addr} connected.")
        while True:
            #first receive the length of message
            msg_length = conn.recv(HEADER).decode()
            if not msg_length:
                print("OOPS SOMETHING WENT WRONG")
                break

            print(get_time() + f"Received data from address {addr}")
            print("MSG_LENGTH:", msg_length)
            msg_length = int(msg_length)

            #receive message from client
            data = ""
            while len(data) < msg_length:
                data += conn.recv(msg_length).decode()
                print("DATA: ", data)
            if data == DISCONNECT_MESSAGE:
                print(get_time() + f"{addr} disconnected!")
                conn.close()
                break
            else:
                function_name = data.split(';')[0]
                f_args = data.split(';')[1].split('|') #note this is a list
                f_args.remove('')
                returned_value = self.functions[function_name](f_args)
                returned_value = str(returned_value) + '\n'
                conn.send(returned_value.encode())

    #just a testing function
    def fucker(self, args):
        print(args)
        return 1

    def check_user(self, args):
        #check if the user and pass are viable; return true if they are
        for key in self.controller.users:
            if key == args[0] and self.controller.users[key] == args[1]:
                return 1
        
        return 0
    
    def sign_up (self, args):
        #returns 1 if user is taken, 2 if pass is taken
        #returns 0 if sign up is successful
        for key in self.controller.users:
            if key == args[0] or self.controller.users[key] == args[1]: 
                return 0

            self.controller.users[args[0]] = [args[1], []]
            self.controller.user_text.write (args[0] + " " + args[1] + " | ")
            return 1
    
    def join_community (self, args):
        #args [user, code]; both user and code are strings
        #return 1 for true, 0 for false

        
        user = args[0]
        code = args[1]
        #check if user already has that code meaning user is alreayd in that community
        if code in self.controller.users[user][1]:
            return 0
        #write the new community code to the file
        else: 
            self.controller.users[user][1].append (code)
            
            lines = self.controller.f.readlines()
            
            for i in range (len (lines)):
                entries = lines[i].split ("|")

                user_pass = entries[0][:-1].split (" ")
                if user_pass[0] == user:
                    lines[i] = lines[i] + " " + code
                    self.controller.user_text_edit.write (lines)
                    return 1 
            return 0

    def get_community (self, args):
        return self.controller.users[args][1]

    def create_community (self, args):
        #create community and write it to the file with username who created it underneath
        #args [user, name]
        #return code

        user = args[0]
        name = args[1]

        code = name [:len (name)//2] + str (random.randint(100, 999))

        self.controller.users[user][1].append (code)

        #write it to the file
        lines = self.controller.f.readlines()            
        for i in range (len (lines)):
            entries = lines[i].split ("|")

            user_pass = entries[0][:-1].split (" ")
            if user_pass[0] == user:
                lines[i] = lines[i] + " " + code
                self.controller.user_text_edit.write (lines)
        
        return code



    '''
    For server use

    first argument is community_code
    each task is separated by colon (|)
    each task works as follows (separated by : within task)
    [0] = username
    [1] = destination
    [2] = start
    [3] = finish
    [4] = max orders
    [everything after] = requests
    '''
    #pushes changes to the events
    def push_events (self, args):
        #we receive events and we update dicts
        tasks_list = []
        for task in args[1:]:
            task_list = task.split(':')
            #add time feature where task will delete itself after time is met
            if (len(task_list) - 5) - int(task_list[4]) > 0:
                tasks_list.append(task_list)

        self.controller.communities[args[0]].update_events(tasks_list)
        return 1

    #returns string that will be processed client side
    #basically returns all events of a community
    def pull_events (self, args):
         #response to an event should be added as another parameter? to the list or file events are stored in
        community_events = self.controller.communities[args[0]].events
        temp_string = ""
        for cnt, event in enumerate(community_events):
            temp_string += ":".join(event)
            if cnt < len(community_events) - 1:
                temp_string += '|'
        return temp_string 




    
    


