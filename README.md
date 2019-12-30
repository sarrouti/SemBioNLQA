SemBioNLQA: A semantic biomedical question answering system for retrieving exact and ideal answers to natural language questions
===================

SemBioNLQA - Semantic Biomedical Natural Language Question Answering - has the ability to handle the kinds of yes/no questions, factoid questions, list questions and summary questions that are commonly asked in the biomedical domain. It consists of question classification, document retrieval, passage retrieval and answer extraction modules. It is aimed to be able to accept a variety of natural language  questions and to generate appropriate natural language answers by providing both exact and ideal answers (paragraph-sized summaries). It provides exact  answers “yes” or “no” for yes/no questions, biomedical named entities for factoid questions, and a list of biomedical named entities for list questions.  In addition to exact answers for yes/no, factoid and list questions, SemBioNLQA also returns ideal answers, while it retrieves only ideal answers for summary  questions.

The SemBioNLQA system, dealing with four types of questions, is based on (1) handcrafted lexico-syntactic patterns and a machine learning algorithm for question classification, (2) PubMed search engine and UMLS similarity for document retrieval, (3) the BM25 model, stemmed words and UMLS concepts for passage retrieval, and (4) UMLS metathesaurus, BioPortal synonyms, sentiment analysis and term frequency metric for answer extraction. 


![picture alt](https://github.com/sarrouti/SemBioNLQA/blob/master/SemBioNLQA/Graphical%20abstract-1.jpg "Title is optional")

We implemented SemBioNLQA using JSF 2, PrimeFaces, JavaBean and Tomcat 7.

![picture alt](https://github.com/sarrouti/SemBioNLQA/blob/master/SemBioNLQAp.png "Title is optional")

SemBioNLQA first takes as its input a natural language biomedical question and includes preprocessing of the question, identification of the question type and the expected answer format based on handcrafted lexico-syntactic patterns and support vector machine (SVM), as well as building a query using UMLS entities. Then, based on PubMed and UMLS similarity, it retrieves documents satisfying the query from the MEDLINE database. After that, it extracts top-ranked passages from top-ranked documents based on the BM25 model, stemmed words and UMLS concepts. Finally, it generates and returns both “exact” (depending on the expected answer for each question type) and paragraph-sized “ideal” answers from these passages based on the UMLS metathesaurus, BioPortal synonyms, SENTIWORDNET and term frequency metric.
# Installing #

1. Download the SemBioNLQA web application.

2. Clone the project into a local directory.

3. Instal Tomact 7 which is included in the downloaded archive (https://sites.google.com/site/mouradsarrouti/sembionlqa). We have included all requered libraries in this tomcat.

4. Import SemBioNLQA project in eclipse.

5. Run the application.

# Reference #

If you use this system or any part of this system, please cite our paper:
@article{Sarrouti_2020,
	doi = {10.1016/j.artmed.2019.101767},
	url = {https://doi.org/10.1016%2Fj.artmed.2019.101767},
	year = 2020,
	month = {jan},
	publisher = {Elsevier {BV}},
	volume = {102},
	pages = {101767},
	author = {Mourad Sarrouti and Said Ouatik El Alaoui},
	title = {{SemBioNLQA}: A semantic biomedical question answering system for retrieving exact and ideal answers to natural language questions},
	journal = {Artificial Intelligence in Medicine}
}


# Contact Information: #

For help or issues using SemBioNLQA, please submit a GitHub issue. Please contact Mourad Sarrouti (sarrouti.mourad (at] gmail.com, https://sites.google.com/site/mouradsarrouti/) for communication related to SemBioNLQA.
