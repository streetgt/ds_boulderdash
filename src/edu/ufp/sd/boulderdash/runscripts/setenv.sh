#@REM ************************************************************************************
#@REM Description: run previously all batch files
#@REM Author: Rui S. Moreira
#@REM Date: 20/02/2014
#@REM pwd: /Users/rui/Documents/NetBeansProjects/SD/src/edu/ufp/sd/boulderdash
#@REM http://docs.oracle.com/javase/tutorial/rmi/running.html
#@REM ************************************************************************************

#@REM ======================== Use Shell Parameters ========================
#@REM Script usage: setenv <role> (where role should be: server / client)
export SCRIPT_ROLE=$1

#@REM ======================== CHANGE BELOW ACCORDING YOUR PROJECT and PC SETTINGS ========================
#@REM ==== PC STUFF ====
export JDK=/System/Library/Frameworks/JavaVM.framework/Versions/Current/Commands/java
export USERNAME=tiagocardoso

#@REM ==== JAVA NAMING STUFF ====
export JAVAPROJ_NAME=SD_BoulderDash
export JAVAPROJ=/Users/${USERNAME}/NetBeansProjects/${JAVAPROJ_NAME}
export PACKAGE=boulderdash
export SERVICE_PREFIX=BoulderDash
export CLIENT_CLASS_PREFIX=BoulderDash
export SERVER_CLASS_PREFIX=BoulderDash
export CLIENT_CLASS_POSTFIX=Client
export SERVER_CLASS_POSTFIX=Server
export SETUP_CLASS_POSTFIX=Setup
export SERVANT_IMPL_CLASS_POSTFIX=Impl
export SERVANT_ACTIVATABLE_IMPL_CLASS_POSTFIX=ActivatableImpl

#@REM ==== NETWORK STUFF ====
export REGISTRY_HOST=194.210.211.10
export REGISTRY_PORT=1099
export SERVER_RMI_HOST=${REGISTRY_HOST}
export SERVER_RMI_PORT=1098
export SERVER_CODEBASE_HOST=${SERVER_RMI_HOST}
export SERVER_CODEBASE_PORT=8000
export CLIENT_RMI_HOST=${REGISTRY_HOST}
export CLIENT_RMI_PORT=1097
export CLIENT_CODEBASE_HOST=${CLIENT_RMI_HOST}
export CLIENT_CODEBASE_PORT=8000

#@REM ======================== DO NOT CHANGE AFTER THIS POINT ========================
export JAVAPACKAGE=edu.ufp.sd.${PACKAGE}
export JAVAPACKAGEROLE=edu.ufp.sd.${PACKAGE}.${SCRIPT_ROLE}
export JAVAPACKAGEPATH=edu/ufp/sd/${PACKAGE}/${SCRIPT_ROLE}
export JAVASCRIPTSPATH=edu/ufp/sd/${PACKAGE}/runscripts
export JAVASECURITYPATH=edu/ufp/sd/${PACKAGE}/securitypolicies
export SERVICE_NAME=${SERVICE_PREFIX}Service
export SERVICE_URL=rmi://${REGISTRY_HOST}:${REGISTRY_PORT}/${SERVICE_NAME}
export SERVANT_ACTIVATABLE_IMPL_CLASS=${JAVAPACKAGEROLE}.${SERVER_CLASS_PREFIX}${SERVANT_ACTIVATABLE_IMPL_CLASS_POSTFIX}
export SERVANT_PERSISTENT_STATE_FILENAME=${SERVICE_PREFIX}Persistent.State

export PATH=${PATH}:${JDK}/bin

export NETBEANS_CLASSES=build/classes/
export NETBEANS_SRC=src

#java.rmi.server.codebase property defines the location where the client/server provides its classes.
#export CODEBASE=file:///${JAVAPROJ}/${NETBEANS_CLASSES}
export SERVER_CODEBASE=http://${SERVER_CODEBASE_HOST}:${SERVER_CODEBASE_PORT}/${NETBEANS_CLASSES}
export CLIENT_CODEBASE=http://${CLIENT_CODEBASE_HOST}:${CLIENT_CODEBASE_PORT}/${NETBEANS_CLASSES}

#Policy tool editor: /Library/Java/JavaVirtualMachines/jdk1.8.0_25.jdk/Contents/Home/bin/policytool
export SERVER_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/serverAllPermition.policy
export CLIENT_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/clientAllPermition.policy
export SETUP_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/setup.policy
export RMID_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/rmid.policy
export GROUP_SECURITY_POLICY=file:///${JAVAPROJ}/${NETBEANS_SRC}/${JAVASECURITYPATH}/group.policy

export MYCLASSES_FOLDER=${JAVAPROJ}/${NETBEANS_CLASSES}
export MYCLASSES_JAR=${JAVAPROJ}/dist/SD.jar

export CLASSPATH=${JAVAPROJ}/${NETBEANS_CLASSES}

export ABSPATH2CLASSES=${JAVAPROJ}/${NETBEANS_CLASSES}
export ABSPATH2SRC=${JAVAPROJ}/${NETBEANS_SRC}