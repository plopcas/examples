
#sample-spring-boot-rest-docker

##Boot2docker

Init boot2docker.
Connect to VM using port forwarding (it will not work if you just ssh).
```
boot2docker init
boot2docker up
boot2docker ssh -L 8090:localhost:8090
```

In Windows, share your workspace (right click / Share).
Use your IP or host name, and your windows username and password.
```
wget http://distro.ibiblio.org/tinycorelinux/5.x/x86/tcz/cifs-utils.tcz
tce-load -i cifs-utils.tcz
sudo mkdir /c/workspace
sudo mount -t cifs //WINDOWS_IP/workspace /c/workspace -o username=WINDOWS_USERNAME
```

Move to the project directory.

```
cd /c/workspace/sample-spring-boot-rest-docker
```

##Docker

Build the image.
```
docker build -t=ssbr .
```

Run the container.

```
docker run -it -p 8090:8090 ssbr
```
OR
```
docker run -d -p 8090:8090 ssbr
```

In your browser:

[http://localhost:8090/health](http://localhost:8090/health)

[http://localhost:8090/hello-world](http://localhost:8090/hello-world)

```

+--VirtualBox-------------------------------+
|                                           |
|                                           |
|    +--Boot2docker----------------------+  |
|    |                                   |  |
|    |                                   |  |
|    |    +--Docker-------------------+  |  |
|    |    |                           |  |  |
|    |    |                           |  |  |
|    |    |    +--SpringBoot-------+  |  |  |
|    |    |    |XXXXXXXXXXXXXXXXXXX|  |  |  |
|    |    |    |XXXXXXXXXXXXXXXXXXX|  |  |  |
|    |    |    |XXXXXXXXXXXXXXXXXXX|  |  |  |
|    |    |    |XXXXXXXXXXXXXXXXXXX|  |  |  |
|    |    |    |XXXXXXXXXXXXXXXXXXX|  |  |  |
|    |    |    |XXXXXXXXXXXXXXXXXXX|  |  |  |
|    |    |    +-------------------+  |  |  |
|    |    |                           |  |  |
|    |    +---------------------------+  |  |
|    |                                   |  |
|    +-----------------------------------+  |
|                                           |
+-------------------------------------------+


```
