# Examen

Para ejecutar el docker ejecutar el comando 

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
