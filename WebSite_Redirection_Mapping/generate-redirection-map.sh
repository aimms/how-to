#Inspired by https://docs.openstack.org/doc-contrib-guide/redirects.html

#Stores the current repo branch in var
var=$(git branch | grep \* | cut -d ' ' -f2 )

#Output the mapping file in the format "<original file location>\t<new file location>"
git log $var --name-status --format='%H' --after='2018-24-01' | grep ^R | grep .rst | cut -f2- > WebSite_Redirection_Mapping\\redirection_map_full.txt