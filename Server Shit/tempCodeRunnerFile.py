
def server_loop(self):
    pass

def check_user (user, pwrd):
    lines = open ("community_info.txt", "r", encoding="latin-1").readlines()

    for line in lines: 
        # print (line)
        entries = line.split ("|")

        print (entries[0].replace (" ", "") + "fuk")
        print (entries[1].replace (" ", "") + "fuk")
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
    