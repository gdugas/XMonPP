/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmonpp.io;

/**
 *
 * @author guillaume
 */
public interface Filter {

    public boolean inputFiltering(Input input);

    public void onInputError(Input input);

    public void outputFiltering(Input input);

    public void outputFiltering(Output output);
}
