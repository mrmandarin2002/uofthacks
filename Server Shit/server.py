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

class server():

    def load_files(self):
        users = {}
        

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
            print(get_time() + f" New client connected. There are currently {threading.activeCount() - 1} client(s) connected!")

if __name__ == "__main__":
    s = server()