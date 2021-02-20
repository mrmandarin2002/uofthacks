import socket, threading

PORT = 5050
FORMAT = 'utf-8'
DISCONNECT_MESSAGE = "!DISCONNECT"
HEADER = 16

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