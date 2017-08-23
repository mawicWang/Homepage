var cavas = document.getElementById("myCanvas");
var ctx = cavas.getContext("2d");

var EleTypes = {
    red: {
        id: 1,
        fill: "#ff7575"
    },
    blue: {
        id: 2,
        fill: "#0080FF"
    },
    yellow: {
        id: 3,
        fill: "#FFDC35"
    },
    green: {
        id: 4,
        fill: "#D3FF93"
    },
    purple: {
        id: 5,
        fill: "#C07AB8"
    },
    shit: {
        id: 6,
        fill: "#9F5000"
    },
    mint: {
        id: 7,
        fill: "#80FFFF"
    }

};

function getAvailableEles() {
    return [EleTypes.red, EleTypes.blue, EleTypes.yellow, EleTypes.green, EleTypes.purple, EleTypes.shit, EleTypes.mint];
}

var eleProperty = {
    width: 50,
    height: 50,
    padding: 5
};
var boardProperty = {
    width: 16,
    height: 10
}

function Board() {
    var $that = this;
    this.width = 16;
    this.height = 10;
    this.matrix = [];
    this.selectedEle = [];
    this.processingAnimationNum = 0;
    this.timer = null;
    this.movingAnimations = [];

    for (var i = 0; i < this.width; i++) {
        this.matrix[i] = [];
    }

    this.get = function (x, y) {
        return this.matrix[x][y];
    };
    this.set = function (x, y, eleType) {
        this.matrix[x][y] = new Element(x, y, eleType);
        return this.matrix[x][y];
    };

    this.addMovingAinimation = function (ele, offsetX, offsetY) {
        var eleToMove = {
            ele: ele,
            offsetX: offsetX,
            offsetY: offsetY,
            distance: Math.sqrt(offsetX * offsetX + offsetY * offsetY) * eleProperty.width,
            stepDistance: 0
        };
        this.movingAnimations.push(eleToMove);

        var endMovingTempList = [];
        var toCheckEliminationList = {
            list: [],
            push: function (ele) {
                this.list.push(ele);
            },
            pushOrReplace: function (ele) {
                this.list = $.grep(this.list, function (a) {
                    return a.x == ele.x && a.y == ele.y;
                }, true);
                this.list.push(ele);
            },
            clear: function () {
                this.list = [];
            }
        };

        if ($that.timer == null) {
            $that.processingAnimationNum++;
            $that.timer = requestAnimationFrame(function fn() {
                if ($that.movingAnimations.length > 0) {
                    for (var i in $that.movingAnimations) {
                        if ($that.movingAnimations[i].distance == $that.movingAnimations[i].stepDistance) {
                            endMovingTempList.push($that.movingAnimations[i]);
                            continue;
                        }

                        // prepare clear for repaint
                        $that.movingAnimations[i].ele.paintClearElement();
                    }

                    // end moving
                    for (var i in endMovingTempList) {
                        var ani = endMovingTempList[i];
                        $that.set(ani.ele.x + ani.offsetX, ani.ele.y + ani.offsetY, ani.ele.eleType).paintClearElement();
                        $that.get(ani.ele.x + ani.offsetX, ani.ele.y + ani.offsetY).paintElement();
                        toCheckEliminationList.pushOrReplace($that.get(ani.ele.x + ani.offsetX, ani.ele.y + ani.offsetY));
                        $that.movingAnimations = $.grep($that.movingAnimations, function (a) {
                            return a != ani;
                        });
                    }

                    for (var i in $that.movingAnimations) {
                        //move step
                        var remainAni = $that.movingAnimations[i];
                        remainAni.stepDistance += 5;

                        if (remainAni.stepDistance > remainAni.distance) {
                            remainAni.stepDistance = remainAni.distance;
                        }

                        // stepOffset/offset = stepDistance/distance
                        remainAni.ele.paintElement(eleProperty.width * remainAni.offsetX * remainAni.stepDistance / remainAni.distance,
                            eleProperty.width * remainAni.offsetY * remainAni.stepDistance / remainAni.distance);

                    }
                    requestAnimationFrame(fn);
                } else {
                    cancelAnimationFrame($that.timer);
                    $that.timer = null;

                    $that.processingAnimationNum--;

                    var toEliminate = checkElimination(toCheckEliminationList.list);
                    eliminate(toEliminate);
                    toCheckEliminationList.clear();
                }
            });
        }
    };

    function eliminate(toEliminate) {
        console.log("toEliminate");
        console.log(toEliminate);
        if (!toEliminate || toEliminate.length == 0) {
            return;
        }

        var dropdownList = {
            list: [],
            fetchByXY: function (x, y) {
                for (var i in this.list) {
                    if (this.list[i].ele.x == x && this.list[i].ele.y == y) {
                        return this.list[i].ele;
                    }
                }
                return null;
            },
            fetch: function (ele) {
                for (var i in this.list) {
                    if (this.list[i].ele == ele) {
                        return this.list[i];
                    }
                }
                return null;
            },
            add: function (ele, distance) {
                if (this.fetch(ele)) {
                    this.fetch(ele).distance++;
                } else {
                    var obj = {
                        ele: ele,
                        distance: 1
                    }
                    if (distance) {
                        obj.distance = distance;
                    }
                    this.list.push(obj);
                }
            },
            iterator: function (callback) {
                for (var i in this.list) {
                    if (callback && typeof callback === 'function')
                        callback(this.list[i].ele, this.list[i].distance);
                }
            }
        };

        for (var i in toEliminate) {
            toEliminate[i].paintClearElement();
            loopToUp(toEliminate[i])
        }

        console.log(dropdownList)
        dropdownList.iterator(function (ele, d) {
            //ele.paintClearElement();
            board.addMovingAinimation(ele, 0, d);
        });

        function loopToUp(ele) {
            var up = ele.up();
            if (!up) {
                for (var i = -1; ; i--) {
                    var newEle = dropdownList.fetchByXY(ele.x, i);
                    if (newEle) {
                        dropdownList.add(newEle);
                    } else {
                        newEle = new Element(ele.x, i, nextRandomEle(getAvailableEles()));
                        dropdownList.add(newEle, -i);
                        break;
                    }
                }
                return;
            }
            if ($.inArray(up, toEliminate) == -1) {
                dropdownList.add(up);
            }
            loopToUp(up);
        }
    }

    function Element(x, y, eleType) {
        this.x = x;
        this.y = y;
        this.eleType = eleType;
        this.clicked = false;
        this.left = function () {
            if (x == 0)
                return null;
            return $that.matrix[x - 1][y];
        }
        this.right = function () {
            if (x == $that.width - 1)
                return null;
            return $that.matrix[x + 1][y];
        }
        this.up = function () {
            if (y == 0)
                return null;
            return $that.matrix[x][y - 1];
        }
        this.down = function () {
            if (y == $that.height - 1)
                return null;
            return $that.matrix[x][y + 1];
        }
        this.toggleClick = function () {
            if (this.clicked = !this.clicked) {
                $that.selectedEle.push(this);
            } else {
                $that.selectedEle = [];
            }
            this.paintElementSelect();

            if ($that.selectedEle.length == 2) {
                this.exchangeWith($that.selectedEle[0]);
            }
        }
        this.exchangeWith = function (ele) {
            var $this = this;
            var directionX = ele.x - this.x;
            var directionY = ele.y - this.y;
            if (directionX * directionX + directionY * directionY > 1) {
                setTimeout(function () {
                    $that.selectedEle[0].toggleClick()
                    $this.clicked = false;
                    $this.paintElementSelect();
                }, 200);
                return;
            }

            $that.selectedEle[0].toggleClick()
            $this.clicked = false;

            $that.addMovingAinimation(this, directionX, directionY);
            $that.addMovingAinimation(ele, -directionX, -directionY);

        }
        this.paintElement = function (offsetX, offsetY) {
            offsetX = offsetX ? offsetX : 0;
            offsetY = offsetY ? offsetY : 0;
            // console.log("paint element (" + this.x + "," + this.y + "," + this.eleType.fill + "," + offsetX + "," + offsetY + ")");

            ctx.fillStyle = this.eleType.fill;
            ctx.strokeStyle = "black";
            ctx.fillRect(this.x * eleProperty.width + eleProperty.padding + offsetX,
                this.y * eleProperty.height + eleProperty.padding + offsetY,
                eleProperty.width - eleProperty.padding * 2,
                eleProperty.height - eleProperty.padding * 2);
            ctx.strokeRect(this.x * eleProperty.width + eleProperty.padding + offsetX,
                this.y * eleProperty.height + eleProperty.padding + offsetY,
                eleProperty.width - eleProperty.padding * 2,
                eleProperty.height - eleProperty.padding * 2);
            ctx.fillStyle = "black";
            // ctx.fillText(this.x + "," + this.y, this.x * eleProperty.width + 15, this.y * eleProperty.width + 25);

        }
        this.paintClearElement = function () {
            ctx.fillStyle = "white";
            ctx.fillRect(this.x * eleProperty.width, this.y * eleProperty.width, eleProperty.width, eleProperty.height);
        }
        this.paintMarkElementDebug = function () {
            ctx.strokeStyle = "black";
            ctx.moveTo(this.x * eleProperty.width, this.y * eleProperty.width);
            ctx.lineTo(this.x * eleProperty.width + eleProperty.width, this.y * eleProperty.width + eleProperty.height);
            ctx.stroke();
            ctx.moveTo((this.x + 1) * eleProperty.width, this.y * eleProperty.width);
            ctx.lineTo(this.x * eleProperty.width, this.y * eleProperty.width + eleProperty.height);
            ctx.stroke();
        }
        this.paintElementSelect = function () {
            if (this.clicked) {
                ctx.strokeStyle = "black";
            } else {
                ctx.strokeStyle = "white";
            }
            ctx.strokeRect(this.x * eleProperty.width + eleProperty.padding / 2,
                this.y * eleProperty.height + eleProperty.padding / 2,
                eleProperty.width - eleProperty.padding,
                eleProperty.height - eleProperty.padding);
        }
    }
};

var board = new Board();

function initBoard(eles) {
    // 填色块
    for (var i = 0; i < boardProperty.width; i++) {
        for (var j = 0; j < boardProperty.height; j++) {
            var eleType = nextRandomEle(eles);
            board.set(i, j, eleType);
            while (isLine(board.get(i, j))) {
                eleType = nextRandomEle(eles);
                board.set(i, j, eleType);
            }
            board.get(i, j).paintElement();
        }
    }
}

function nextRandomEle(eles) {
    var eleTypeId = Math.floor(eles.length * Math.random());
    return eles[eleTypeId];
}


function isLine(ele) {
    if (ele.left() && ele.left().left()) {
        if (ele.left().eleType == ele.eleType && ele.left().left().eleType == ele.eleType) {
            // console.log("left is line " + ele.x + " " + ele.y);
            return true;
        }
    }
    if (ele.right() && ele.right().right()) {
        if (ele.right().eleType == ele.eleType && ele.right().right().eleType == ele.eleType) {
            // console.log("right is line " + ele.x + " " + ele.y);
            return true;
        }
    }
    if (ele.up() && ele.up().up()) {
        if (ele.up().eleType == ele.eleType && ele.up().up().eleType == ele.eleType) {
            // console.log("up is line " + ele.x + " " + ele.y);
            return true;
        }
    }
    if (ele.down() && ele.down().down()) {
        if (ele.down().eleType == ele.eleType && ele.down().down().eleType == ele.eleType) {
            // console.log("down is line " + ele.x + " " + ele.y);
            return true;
        }
    }

    return false;
}

function checkElimination(ele) {
    console.log("checkElimination");
    console.log(ele);
    var toEliminationList = {
        list: [],
        add: function (ele) {
            var $t = this;
            if (ele instanceof Array) {
                for (var i in ele) {
                    $t.add(ele[i]);
                }
            } else {
                if ($.grep(this.list, function (a) {
                        return a.x == ele.x && a.y == ele.y;
                    }).length == 0) {
                    this.list.push(ele);
                }
            }
        }
    };
    var tempListHoriz, tempListVert;
    for (var i in  ele) {
        tempListHoriz = [];
        tempListVert = [];
        seekLeft(ele[i]);
        seekRight(ele[i]);
        seekUp(ele[i]);
        seekDown(ele[i]);

        if (tempListHoriz.length > 1) {
            toEliminationList.add(tempListHoriz);
            toEliminationList.add(ele[i]);
        }
        if (tempListVert.length > 1) {
            toEliminationList.add(tempListVert);
            toEliminationList.add(ele[i]);

        }
    }

    return toEliminationList.list;

    function seekLeft(e) {
        if (e.left() && e.left().eleType == e.eleType) {
            tempListHoriz.push(e.left());
            seekLeft(e.left());
        }
        return;
    }


    function seekRight(e) {
        if (e.right() && e.right().eleType == e.eleType) {
            tempListHoriz.push(e.right());
            seekRight(e.right());
        }
        return;
    }

    function seekUp(e) {
        if (e.up() && e.up().eleType == e.eleType) {
            tempListVert.push(e.up());
            seekUp(e.up());
        }
        return;
    }

    function seekDown(e) {
        if (e.down() && e.down().eleType == e.eleType) {
            tempListVert.push(e.down());
            seekDown(e.down());
        }
        return;
    }

}

function fixRequestAnimationFrame() {
    if (!window.requestAnimationFrame) {

        window.requestAnimationFrame = (function () {

            return window.webkitRequestAnimationFrame ||
                window.mozRequestAnimationFrame ||
                window.oRequestAnimationFrame ||
                window.msRequestAnimationFrame ||
                function (/* function FrameRequestCallback */ callback, /* DOMElement Element */ element) {
                    window.setTimeout(callback, 1000 / 60);
                };

        })();

    }
}

function cavasClickListener(e) {
    if (board.processingAnimationNum != 0) {
        return;
    }
    if (first) {
        return;
    }
    var cavasBoundingRect = cavas.getBoundingClientRect();
    var mouseX = (e.clientX - cavasBoundingRect.left);
    var mouseY = (e.clientY - cavasBoundingRect.top);

    var eleX = Math.floor(mouseX / eleProperty.width);
    var eleY = Math.floor(mouseY / eleProperty.height);

    board.get(eleX, eleY).toggleClick();
}

function cavasMouseDownListener(e) {
    if (board.processingAnimationNum != 0) {
        return;
    }

    first = null;
    cavas.addEventListener("mousemove", cavasMouseMoveListener);
    cavas.addEventListener("mouseup", cavasMouseUpListener);
}

var first;

function cavasMouseMoveListener(e) {
    if (board.processingAnimationNum != 0) {
        return;
    }
    var cavasBoundingRect = cavas.getBoundingClientRect();
    var mouseX = (e.clientX - cavasBoundingRect.left);
    var mouseY = (e.clientY - cavasBoundingRect.top);

    var eleX = Math.floor(mouseX / eleProperty.width);
    var eleY = Math.floor(mouseY / eleProperty.height);

    if (!first) {
        first = {};
        first.x = eleX;
        first.y = eleY;

    }
    if (first && (first.x != eleX || first.y != eleY)) {
        board.get(first.x, first.y).toggleClick();
        board.get(eleX, eleY).toggleClick();

        first = null;
        cavas.removeEventListener("mousemove", cavasMouseMoveListener);
        cavas.removeEventListener("mouseup", cavasMouseUpListener);
    }
    ;
}

function cavasMouseUpListener(e) {
    if (board.processingAnimationNum != 0) {
        return;
    }

    e.preventDefault();

    first = null;
    cavas.removeEventListener("mousemove", cavasMouseMoveListener);
    cavas.removeEventListener("mouseup", cavasMouseUpListener);
}


$(document).ready(function () {
    fixRequestAnimationFrame();
    initBoard(getAvailableEles());
    cavas.addEventListener("click", cavasClickListener);
    cavas.addEventListener("mousedown", cavasMouseDownListener);
});
