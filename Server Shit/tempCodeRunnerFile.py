
def server_loop(self):
    pass

def check_user (user, pwrd):
    lines = open ("user_info.txt", "r", encoding="latin-1").readlines()

    for line in lines: 
        # print (line)
        entries = line.split ("|")

        user_pass = entries[0][:-1].split(" ")
        code = entries[1][1:-1].split (" ")
        print (user_pass[0] )
        print (code [0])
        print (code[1])
        # print (com_code + "nice"

def sign_up (user, pwrd):
    f = open ("user_info.txt", "r", encoding="latin-1")
    lines = f.readlines()
    f.close()
    
    for line in lines:
        if line.find (user + " " + pwrd) != -1:
            return False
    # f.write (user + " " + pwrd + " |")
    g = open ("user_info.txt", "a")
    g.write ("\n" + user + " " + pwrd + " | ")
    return True

    
check_user ("vincent", "tang")
# print (check_user ("vincent", "praxisfucks"))

# print (sign_up ("vincent", "praxisfucks"))
# print (sign_up ("derekma", "fraxispucks"))
# print (sign_up ("v", "praxisfucks"))
    