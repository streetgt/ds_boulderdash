#REM ************************************************************************************
#REM Description: run BoulderDashClient
#REM Author: Rui S. Moreira
#REM Date: 20/02/2014
#REM ************************************************************************************
#REM Script usage: runclient <role> (where role should be: server / client)
#source ./setclientenv.sh
source ./setenv.sh client

cd ${ABSPATH2CLASSES}
#clear
#pwd  
#java 
#     #Root directory of client program/class (class path)
#     -cp classpath \

#     #Property defining security policy, i.e., permissions to grant to code/classes
#     -Djava.security.policy=client.policy \

#     #Property defining URL where client serves its classes
#     -Djava.rmi.server.codebase=${SERVER_CODEBASE}

#     #Property defining URL of client class (this property is used inside client.policy file to grant permissions to client class)
#     -Dexamples.activation.client.codebase=${CLIENT_CODEBASE} \

#     #Property defining the name of the service (this property is used inside main() for registry lookup)
#     -Dexamples.activation.servicename=${SERVICE_NAME} \

#     examples.activation.client.Client ${SERVER_RMI_HOST} ${SERVER_RMI_PORT}
java -cp ${CLASSPATH} \
     -Djava.security.policy=${CLIENT_SECURITY_POLICY} \
     -Djava.rmi.server.codebase=${SERVER_CODEBASE} \
     -D${JAVAPACKAGEROLE}.codebase=${CLIENT_CODEBASE} \
     -D${JAVAPACKAGE}.servicename=${SERVICE_NAME} \
     ${JAVAPACKAGEROLE}.${CLIENT_CLASS_PREFIX}${CLIENT_CLASS_POSTFIX} ${SERVER_RMI_HOST} ${SERVER_RMI_PORT}

cd ${ABSPATH2SRC}/${JAVASCRIPTSPATH}
#pwd