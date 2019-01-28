
from socket import *
import socket
import os
import struct
from thread import *
import threading 
import time
import random 

print_lock = threading.Lock() 
  
def threaded(c): 
    while True: 
  
        data = c.recv(1024) 
        if not data: 
            print('Bye') 
              
            # lock released on exit 
            print_lock.release() 
            break
  
        time.sleep(1)
        
        if data == 'Forward':
            print('Go Forward')
            os.system("python forward.py")
        elif data == 'bacAndLeft':
            print('BackAndLeft')
            os.system("python backAndLeft.py")
            time.sleep(5)
        elif data == 'bacAndRight':
            print('BackAndRight')
            os.system("python backAndRight.py")
            time.sleep(5)
            
        data1 = repr(distance1)+','
        data2 = repr(distance2)+','
        data3 = repr(distance3)
        
        data = data1+data1+data3
  
        c.send(data)
  
    c.close() 
  
  
def Main(): 
    host = 'localhost'
    port = 7000
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.bind((host, port)) 
    print("Socket: ", port) 
  
    s.listen(1) 
    print("Trwa wyczekiwanie") 
  
    while True: 
  
        c, addr = s.accept() 
  
        print_lock.acquire() 
        print('Connected to :', addr[0], ':', addr[1]) 
  
        start_new_thread(threaded, (c,)) 
    s.close() 
  
  
if __name__ == '__main__': 
    Main() 
