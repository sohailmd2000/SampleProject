Orginal Code von:
http://www.sump.org/projects/rmiviewer/

Folgende Adaptionen vorgenommen durch Jens Grubert:
setSecurityManager hinzugefügt


Folgende VM müssen in einer RunConfig gesetzt werden:

-Djava.security.policy=policies/default.policy
-Djava.rmi.server.codebase=http://localhost/path/to/codebase
-Dorg.sump.showrmiregistry.defaultregistry=rmi://localhost:1099

Hier muss insbesondere der Pfad zur codebase (java.rmi.server.codebase) angepasst werden. 
Siehe auch:
http://docs.oracle.com/javase/1.4.2/docs/guide/rmi/codebase.html
http://docs.oracle.com/javase/1.4.2/docs/guide/rmi/javarmiproperties.html