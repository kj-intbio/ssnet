# ssnet
single source integration of probabilistic functional integrated networks

ssnet takes a single database of functional interaction data and produces a probabilitistic functional integrated network using the method of:

xxxxx

usage: ssnet inputfile taxid

ssnet is designed to integrate data in biogrid tab3 format, but can integrate any dataset by specifying column numbers for the following variables:

- entity_a
- entity_b
- pubmed id
- type
- taxid

for example:

ssnet inputfile xx xx xx xx xx

Note that the scoring algorithm only works where interaction entities are of the same type; for bipartite intagration use ssbipnet (xxxx).

# ssnet dataset parsing


