
def server_loop(self):
    pass

def check_user (user, pwrd):
    lines = open ("user_info.txt", "r", encoding="latin-1").readlines()

    for line in lines: 
        # print (line)
        entries = line.split ("|")

        real_user = entries[0].split(" ")[0]
        real_pwrd = entries[0].split(" ")[1]

        com_code = entries[1].replace(" ", "")
        # print (real_user, real_pwrd)
        print (com_code + "nice")
        if com_code == "":
            print (com_code, "lol")

        if user == real_user and pwrd == real_pwrd: 
            return True

    return False

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

    
print (check_user ("vincent", "tang"))
# print (check_user ("vincent", "praxisfucks"))

# print (sign_up ("vincent", "praxisfucks"))
# print (sign_up ("derekma", "fraxispucks"))
# print (sign_up ("v", "praxisfucks"))
    