/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ca.uwo.csd.ai.nlp.mallet.libsvm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import cc.mallet.pipe.CharSequence2TokenSequence;
import cc.mallet.pipe.FeatureSequence2FeatureVector;
import cc.mallet.pipe.Input2CharSequence;
import cc.mallet.pipe.Pipe;
import cc.mallet.pipe.PrintInputAndTarget;
import cc.mallet.pipe.SerialPipes;
import cc.mallet.pipe.Target2Label;
import cc.mallet.pipe.TokenSequence2FeatureSequence;
import cc.mallet.pipe.TokenSequenceLowercase;
import cc.mallet.pipe.TokenSequenceRemoveStopwords;
import cc.mallet.types.Instance;
import cc.mallet.types.InstanceList;

/**
 * @author Sharath
 * 
 */
public class BuildPipe {

    /**
     * An instance of Pipe is created which will be used to call the CreatePipe
     * method of the BuildPipe class.
     **/
    private Pipe pipe = null;
    /**
     * An instance of Instancelist is created which is used to add all the
     * instances
     */
    private InstanceList instancelist = null;

    /**
     * @param charseq
     *            An instance of Input2CharSequence Pipe
     * @param tokenseq
     *            An instance of CharSequence2TokenSequence Pipe
     * @param tokenseq2lowercase
     *            Boolean value.
     * @param removestopwards
     *            An instance of TokenSequenceRemoveStopwords Pipe
     * @param tokenseq2featureseq
     *            An instance of TokenSequence2FeatureSequence pipe
     * @param target2label
     *            An instance of Target2Label pipe
     * @param featseq2Featvector
     *            An instance of FeatureSequence2AugmentableFeatureVector pipe
     * @param printinputandlabel
     *            Boolean Value
     * @throws IOException
     */
    @SuppressWarnings("unused")
    public void CreatePipe(Input2CharSequence charseq,
            CharSequence2TokenSequence tokenseq, boolean tokenseq2lowercase,
            TokenSequenceRemoveStopwords removestopwards,
            TokenSequence2FeatureSequence tokenseq2featureseq,
            Target2Label target2label,
            FeatureSequence2FeatureVector featseq2Featvector,
            boolean printinputandlabel) throws IOException {

        if (charseq == null || tokenseq == null)
            throw new IllegalArgumentException(
                    "WrapperClassifier : Input2CharSequence or CharSequence2TokenSequence cannot be null");

        ArrayList<Pipe> pipelist = new ArrayList<Pipe>();

        boolean res = pipelist.add(charseq);
        // if (res == false)
        // throw

        pipelist.add(tokenseq);

        if (tokenseq2lowercase == true) {
            TokenSequenceLowercase lowercase = new TokenSequenceLowercase();
            pipelist.add(lowercase);
        }

        if (removestopwards != null)
            pipelist.add(removestopwards);

        if (tokenseq2featureseq != null)
            pipelist.add(tokenseq2featureseq);

        if (target2label != null)
            pipelist.add(target2label);

        if ((featseq2Featvector == null && tokenseq2featureseq != null)
                || (featseq2Featvector != null && tokenseq2featureseq == null))
            throw new IllegalArgumentException(
                    "WrapperClassifier,BuildPipe: Invalid FeatureSequence2FeatureVector and/or TokenSequence2FeatureSequence parameter");

        if (featseq2Featvector != null)
            pipelist.add(featseq2Featvector);

        if (printinputandlabel == true)
            pipelist.add(new PrintInputAndTarget("abc"));

        pipe = new SerialPipes(pipelist);
        this.instancelist = new InstanceList(pipe);
    }

    /**
     * @param iterator
     */
    public void addThruPipe(Iterator<Instance> iterator) {
        this.instancelist.addThruPipe(iterator);
    }

    /**
     * @return InstanceList
     */
    public InstanceList getInstanceList() {
        return this.instancelist;
    }
}