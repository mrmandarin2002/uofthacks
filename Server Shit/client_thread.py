import socket, threading
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
        threading.Thread(target = self.handle_client, args = (conn, addr)).start()

    def handle_client(self, conn, addr):
        print(get_time() + f"Client with address {addr} connected.")
        while True:
            pass

    def check_user (self, user, pwrd):
        #check if the user and pass are viable; return true if they are
        return True
    
    def sign_up (self, user, pwrd):
        pass
    
    def get_community (self):
        #get communities that the user is part of 
        #or search communities for user and return their code
        pass

    def create_community (self, user):
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

    


