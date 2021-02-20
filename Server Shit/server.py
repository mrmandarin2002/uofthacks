import socket
from datetime import datetime

import client_thread 

PORT = 5055
FORMAT = 'utf-8'
DISCONNECT_MESSAGE = "!DISCONNECT"
HEADER = 16

# function to get the current time
def get_time():
    return str(datetime.now()).split()[1].split(".")[0]+" "

class event():

    def add_order(self):
        pass

    def __init__(self, event_creator, destination, start_time, end_time, max_orders):
        self.orders = []

class community():

    #returns True if successfully added
    #returns False is user already in community
    def add_user(self, username):
        if username not in self.users:
            self.users.append(username)
            return True
        else:
            return False

    def remove_user(self, username):
        self.users.remove(username)

    def add_event(self, event_creator, destination, start_time, end_time, max_orders):
        pass

    def __init__(self, name, code):
        self.name = name
        self.code = code
        self.users = []
        self.events = []



class server():

    def load_files(self):
        self.users = {}
        self.communities = {}
        f = open ("user_info.txt", "r", encoding="latin-1")
        lines = f.readlines()
        f.close

        for line in lines:
            entries = line.split ("|")

            user_pass = entries[0][:-1].split (" ")
            user = user_pass [0]
            pwrd = user_pass [1]
            code = entries[1][1:-1].split (" ")

            self.users [user] = [pwrd, [code]]

        f = open ("community_info.txt", "r", encoding="latin-1")
        lines = f.readlines()
        f.close
        
        for line in lines: 
            entries = line.split ("|")
            code = entries[0].replace (" ", "")
            name = entries[1].replace (" ", "")
            self.communities [code] = community(name, code)



    def __init__(self):
        self.server = socket.socket(socket.AF_INET, socket.SOCK_STREAM) #socket
        self.ip_address = socket.gethostbyname(socket.gethostname())
        self.server_address = (self.ip_address, PORT)
        self.server.bind(self.server_address)
        self.load_files()
        self.client_list = []
        self.server_loop()

    def server_loop(self):
        # main server loop
        print(get_time()+"Starting main server loop...")
        print("Please connect to", self.ip_address)
        self.server.listen()
        while True:
            # receive incoming packages
            conn, addr = self.server.accept()
            self.client_list.append(client_thread.client_thread(conn, addr, self))
            print(get_time() + f" New client connected. There are currently {client_thread.threading.activeCount() - 1} client(s) connected!")

if __name__ == "__main__":
    s = server()