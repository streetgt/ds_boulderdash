#REM ************************************************************************************
#REM Description: run CalculatorClient
#REM Author: Rui S. Moreira
#REM Date: 20/02/2014
#REM ************************************************************************************
#REM Script usage: runclient <role> (where role should be: server / client)
#source ./setclientenv.sh
source ./setenv.sh client

cd ${ABSPATH2CLASSES}
python -m SimpleHTTPServer 8000
cd ${ABSPATH2SRC}/${JAVASCRIPTSPATH}
#pwd