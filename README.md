# ssnet
single source integration of probabilistic functional integrated networks

ssnet takes a BioGRID file of functional interaction data for a species and produces a probabilitistic functional integrated network using the method of:

Aoesha Alsobhe, James Skelton, Matthew Pocock, Simon J. Cockell, Anil Wipat1 and Katherine James (2020) Integration of probabilistic functional integrated networks from a single database source. 


Usage: ssnet inputfile d-value taxid htp_threshold

SSnet currently uses BioGRID data in tab2 format (https://wiki.thebiogrid.org/doku.php/downloads)

**Integration methodology**

Datatsets are split by PubMED identifier and those with number of interactions below the htp_threshold are considered low-throughtput gold standard datasets. Confidence scores were calculated using the methods developed by Lee and colleagues^1, that calculates a log-likelihood score for each dataset:

<img src="https://render.githubusercontent.com/render/math?math=lls^L(E) = \ln  \left(\frac{P(L|E) /\neg P(L|E)} {P(L) /\neg P(L)} \right)">

where, P(L|E) and /\neg P(L|E) represent the frequencies of linkages L observed in a dataset E between genes that are linked and not not linked in the gold standard, respectively, and, P(L) and /\neg P(L) represent the prior expectation of linkages between genes that are linked and not not linked in the gold standard, respectively.

Dataset scores were then integrated using the Lee method using the D value chosen:

![Workflow](/images/integration_workflow.png)


