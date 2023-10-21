#!/bin/zsh

# origin JDK to create the JRE from and use the jlink tool from
JDK_HOME=/Library/Java/JavaVirtualMachines/jdk-21.jdk

# destination directory where to write the generated JRE to, will be created
OUTJREDIR=jre

# comma separated list of additional tools to include in the JRE,
# The jdk.jcmd will add for example jcmd, jmap and other below $JAVA_HOME/bin
TOOLSMODULES=jdk.jcmd

# list of primarily JDK-modules, those will be excluded from the JRE
EXCLUDEMODULES=\
jdk.attach,\
jdk.compiler,\
jdk.editpad,\
jdk.internal.ed,\
jdk.internal.jvmstat,\
jdk.internal.le,\
jdk.internal.opt,\
jdk.jartool,\
jdk.javadoc,\
jdk.jcmd,\
jdk.jconsole,\
jdk.jdeps,\
jdk.jdi,\
jdk.jlink,\
jdk.jpackage,\
jdk.jshell,\
jdk.jstatd,\
jdk.unsupported.desktop,\
jdk.rmic,\
jdk.incubator

exclude=$(echo $EXCLUDEMODULES | sed 's/[, ]/\|/g')

jremodules=$($JDK_HOME/Contents/Home/bin/java --list-modules | cut -d@ -f1 | sort | \
 grep -E -v $exclude | paste -sd)

test -d "$OUTJREDIR" && echo "canceled: directory $OUTJREDIR exists already." && exit 1

$JDK_HOME/Contents/Home/bin/jlink --add-modules $jremodules,$TOOLSMODULES \
 --save-opts $OUTJREDIR/jlink.log --strip-debug --compress 0 \
 --no-header-files --no-man-pages --output $OUTJREDIR

chmod -R og-w $OUTJREDIR

# quick test on the generated JRE:
du -hs $OUTJREDIR
$OUTJREDIR/bin/java -version