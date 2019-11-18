SemBioNLQA: A semantic biomedical question answering system for retrieving exact and ideal answers to natural language questions
===================

SemBioNLQA - Semantic Biomedical Natural Language Question Answering - has the ability to handle the kinds of yes/no questions, factoid questions, list questions and summary questions that are commonly asked in the biomedical domain. It consists of question classification, document retrieval, passage retrieval and answer extraction modules. It is aimed to be able to accept a variety of natural language  questions and to generate appropriate natural language answers by providing both exact and ideal answers (paragraph-sized summaries). It provides exact  answers “yes” or “no” for yes/no questions, biomedical named entities for factoid questions, and a list of biomedical named entities for list questions.  In addition to exact answers for yes/no, factoid and list questions, SemBioNLQA also returns ideal answers, while it retrieves only ideal answers for summary  questions.

The SemBioNLQA system, dealing with four types of questions, is based on (1) handcrafted lexico-syntactic patterns and a machine learning algorithm for question classification, (2) PubMed search engine and UMLS similarity for document retrieval, (3) the BM25 model, stemmed words and UMLS concepts for passage retrieval, and (4) UMLS metathesaurus, BioPortal synonyms, sentiment analysis and term frequency metric for answer extraction. 

Compared with the current state-of-the-art biomedical QA systems, SemBioNLQA, a fully automated system, has the potential to deal with a large amount of question and answer types. SemBioNLQA retrieves quickly users’ information needs by returning exact answers (e.g., “yes”, “no”, a biomedical entity name, etc.) and ideal answers (i.e., paragraph-sized summaries of relevant information) for yes/no, factoid and list questions, whereas it provides only the ideal answers for summary questions. Moreover, experimental evaluations performed on biomedical questions and answers provided by the BioASQ challenge especially in 2015, 2016 and 2017 (as part of our participation), show that SemBioNLQA achieves good performances compared with the most current state-of-the-art systems and allows
a practical and competitive alternative to help information seekers find exact and ideal answers to their biomedical
questions.
![picture alt](https://github.com/sarrouti/SemBioNLQA/blob/master/SemBioNLQA/Graphical%20abstract-1.jpg "Title is optional")

We implemented SemBioNLQA using JSF 2, PrimeFaces, JavaBean and Tomcat 7.

![picture alt](https://github.com/sarrouti/SemBioNLQA/blob/master/SemBioNLQA/SemBioNLQAp.jpg "Title is optional")


# Installing #

1. Download the SemBioNLQA web application.

2. Clone the project into a local directory.

3. Instal Tomact 7 which is included in the downloaded archive (https://sites.google.com/site/mouradsarrouti/sembionlqa). We have included all requered libraries in this tomcat.

4. Import SemBioNLQA project in eclipse.

5. Run the application.

# Reference #

If you use this system or any part of this system, please cite our paper:


# Contact Information: #

For help or issues using SemBioNLQA, please submit a GitHub issue. Please contact Mourad Sarrouti (sarrouti.mourad (at] gmail.com, https://sites.google.com/site/mouradsarrouti/) for communication related to SemBioNLQA.
