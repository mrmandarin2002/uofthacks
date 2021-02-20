import socket, threading, struct
from datetime import datetime

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
            "sign_up" : self.sign_up

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
            users_pass = key.split (" ")
            if users_pass [0] == args[0] and users_pass [1] == args[1]:
                return 1
        
        return 0
    
    def sign_up (self, args):
        #returns 1 if user is taken, 2 if pass is taken
        #returns 0 if sign up is successful
        for key in self.controller.users:
            users_pass = key.split (" ")
            if users_pass[0] == args[0]: 
                return 1
            elif users_pass[1] == args[1]:
                return 2

            self.controller.users[users_pass] = []
            return 0
    
    def get_community (self):
        #get communities that the user is part of 
        #or search communities for user and return their code
        pass

    def create_community (self, args):
        #create community and write it to the file with username who created it underneath
        pass
    
    def get_events (self):
        #get list of events that user is part of
        pass

    def create_event (self):
        #create and event and write it to the file or store it in list
        pass

    def respond_event (self):
        #response to an event should be added as another parameter? to the list or file events are stored in
        pass


    
    


