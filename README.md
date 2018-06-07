# Examen

Para compilar  ejecutar el comando :
docker run -it -v /${RUTAFISICA}/examen/:/codigo kebblar/jdk18-utf8-debug-maven mvn -f /codigo clean package

Para ejecutar el docker con el vertex:
docker run -it -e PBA=1 -p 8081:8080 -v /${RUTAFISICA}/examen/:/codigo kebblar/jdk18-utf8-debug-maven java -jar /codigo/target/sample-1.0-SNAPSHOT-fat.jar

Donde PBA es el nùmero de servidor, si se cargan 6 servidores se deberan nombrar 1,2,3,4,5 y 6.


Para usar haproxy configurar el archivo "/etc/default/haproxy" e ingresa el siguiente codigo:
#*********************************************
# Defaults file for HAProxy
#
# This is sourced by both, the initscript and the systemd unit file, so do not
# treat it as a shell script fragment.

# Change the config file location if needed
CONFIG="/etc/haproxy/haproxy.cfg"
ENABLED=1
# Add extra flags here, see haproxy(1) for a few options
#EXTRAOPTS="-de -m 16"
#*********************************************

Despues configurar el archivo "/etc/haproxy/haproxy.cfg" e ingresar el siguiente codigo al final del archivo:
#*********************************************
frontend www
        bind localhost:9090
        default_backend site-backend
backend site-backend
        mode http
        balance roundrobin
        server lamp1 localhost:8081 check
        server lamp2 localhost:8082 check
        server lamp3 localhost:8083 check
	server lamp4 localhost:8084 check
	server lamp5 localhost:8085 check
	server lamp6 localhost:8086 check
#*********************************************
