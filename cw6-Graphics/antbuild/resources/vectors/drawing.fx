/*###################################################################
### This JavaFX document was generated by Inkscape
### http://www.inkscape.org
### Created: Wed Apr 13 18:15:31 2016
### Version: 0.48.4 r9939
#####################################################################
### NOTES:
### ============
### JavaFX information can be found at
### http://www.javafx.com/
###
### If you have any problems with this output, please see the
### Inkscape project at http://www.inkscape.org, or visit
### the #inkscape channel on irc.freenode.net . 
###
###################################################################*/


/*###################################################################
##   Exports in this file
##==========================
##    Shapes   : 1
##    Nodes    : 14
###################################################################*/


import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.transform.*;
import javafx.scene.paint.*;



public class drawing extends CustomNode {
    /** path path2985 */
    function path2985() : Path {
        Path {
            id: "path2985"
            opacity: 1.00000000
            stroke: Color.rgb(0x00, 0x00, 0x00, 1.00000000)
            strokeWidth: 0.00000000
            strokeLineCap: StrokeLineCap.BUTT
            strokeLineJoin: StrokeLineJoin.MITER
            strokeMiterLimit: 4.00000000
            elements: [
                MoveTo {
                    x: 168.57143000
                    y: -920.00000262
                },
                LineTo {
                    x: 514.28571000
                    y: -674.28571262
                },
                CubicCurveTo {
                    controlX1: 514.28571000
                    controlY1: -674.28571262
                    controlX2: 525.71429000
                    controlY2: -402.85714262
                    x: 511.42857000
                    y: -391.42857262
                },
                CubicCurveTo {
                    controlX1: 497.14286000
                    controlY1: -380.00000262
                    controlX2: 254.28571000
                    controlY2: -251.42857262
                    x: 222.85714000
                    y: -248.57143262
                },
                CubicCurveTo {
                    controlX1: 191.42857000
                    controlY1: -245.71428262
                    controlX2: 125.71429000
                    controlY2: -457.14285262
                    x: 145.71429000
                    y: -480.00000262
                },
                CubicCurveTo {
                    controlX1: 165.71429000
                    controlY1: -502.85714262
                    controlX2: 208.57143000
                    controlY2: -534.28571262
                    x: 225.71429000
                    y: -631.42857262
                },
                CubicCurveTo {
                    controlX1: 242.85714000
                    controlY1: -728.57143262
                    controlX2: 165.71429000
                    controlY2: -805.71428262
                    x: 165.71429000
                    y: -805.71428262
                },
                LineTo {
                    x: 168.57143000
                    y: -920.00000262
                },
                ClosePath {},
            ] // elements
        }; // Path
    } // end path path2985

   override function create(): Node {
       Group {
           content: [
               path2985(),
           ] // content
           transforms: Translate { x : -120.71429000, y : 945.00000262 }
       } // Group
   } // function create()
} // class drawing


/*###################################################################
### E N D   C L A S S    drawing
###################################################################*/


