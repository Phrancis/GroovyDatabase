/**
*  Created by francisveilleux-gaboury on 6/25/15.
*/

import groovy.swing.SwingBuilder
import groovy.beans.Bindable
import static javax.swing.JFrame.EXIT_ON_CLOSE
import java.awt.*

String word

@Bindable
class UserInput {
    String word
    String toString() { "$word" }
}

def userInput = new UserInput(word: '')

def swingBuilder = new SwingBuilder()
swingBuilder.edt {
    lookAndFeel 'nimbus'
    frame (
        title: 'Input',
        size: [350, 230],
        show: true,
        locationRelativeTo: null,
        defaultCloseOperation: EXIT_ON_CLOSE ) {
        borderLayout(vgap: 5)
        panel(constraints:
                BorderLayout.CENTER,
                border: compoundBorder([emptyBorder(10), titledBorder('Input:')])
        ) {
            tableLayout {
                tr {
                    td { label 'Input: ' }
                    td { textField userInput.word, id: word, columns: 20 }
                    println """Word: $word"""
                }
            }
        }
        panel(constraints: BorderLayout.SOUTH) {
            button text: 'Print word', actionPerformed: {
                println """Word: $word"""
            }
        }
    }
}
