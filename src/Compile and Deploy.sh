javac *.java
mv *.class ../bin -f
scp *.java sxd167930@dc01.utdallas.edu:kHopNeighbor/AOSproj1/src/
ssh sxd167930@dc01.utdallas.edu './kHopNeighbor/AOSproj1/src/Compile.sh'
echo 'Deployment complete'
