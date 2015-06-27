import groovy.swing.SwingBuilder
import groovy.beans.Bindable
import static javax.swing.JFrame.EXIT_ON_CLOSE
import java.awt.*

String word

@Bindable
class UserInput {
    String word
}

def userInput = new UserInput(word: null)

def swingBuilder = new SwingBuilder().edt {
    lookAndFeel 'nimbus'
    // frame size
    def width = 350
    def height = 230
    frame (title: 'Input',
            size: [width, height],
            show: true,
            locationRelativeTo: null ) {
        borderLayout(vgap: 5)
        panel(constraints: BorderLayout.CENTER,
                border: compoundBorder([emptyBorder(10), titledBorder('Input:')])) {
            tableLayout {
                tr {
                    td { label 'Input: ' }
                    td { textField id:'input', columns: 20 }
                }
            }
        }
        panel(constraints: BorderLayout.SOUTH) {
            button text: 'Print word', actionPerformed: {
                println """Word: ${userInput.word}"""
            }
        }
        // Bind the text field to the bean
        bean userInput, word: bind { input.text }
    }
}