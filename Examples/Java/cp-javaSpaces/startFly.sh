#! /bin/sh
# 
# File:   startFly.sh
# Created on May 19, 2008, 8:18 AM
#  
# Start the fly server on the given OS and processor
#
#./fly/Linux/i386/fly &
#./fly/MacOSX/i386/fly &
#./fly/MacOSX/ppc/fly &
#./fly/Solaris/i386/fly &
#./fly/Solaris/sparc/fly &
#


OS_NAME=UNKNOWN
ARCH=UNKNOWN

case `uname -s` in 
	(Darwin) OS_NAME=MacOSX;;
	(Linux)  OS_NAME=Linux;;
	(SunOS)  OS_NAME=Solaris;;
esac

case `uname -p` in 
	(i386) ARCH=i386;;
	(powerpc) ARCH=ppc;;
	(sparc) ARCH=sparc;;
	(*) ARCH=i386;;
esac

echo OS $OS_NAME
echo Arch $ARCH

if [ $OS_NAME = UNKNOWN ] || [ $ARCH = UNKNOWN ];
then 
    echo -n Could not recognise `uname -s` `uname -p`
    echo  Please start from the appropriate build directory 
else 
    chmod +x ./fly/$OS_NAME/$ARCH/fly ; ./fly/$OS_NAME/$ARCH/fly $* &
fi

