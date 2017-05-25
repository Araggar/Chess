import java.util.BitSet;

/**
 * Created by Tyrael on 5/4/2017.
 */
 class ChessGame {
    BitBoard blackPawns, blackRooks, blackKnights, blackBishops, blackQueens, blackKing;
    BitBoard whitePawns, whiteRooks, whiteKnights, whiteBishops, whiteQueens, whiteKing;
    BitSet blackBoard = new BitSet(64);
    BitSet whiteBoard = new BitSet(64);
    BitSet fullBoard = new BitSet(64);
    Evaluator eval = new BoardEvaluator();

    ChessGame(BitBoard blackPawns, BitBoard blackRooks, BitBoard blackKnights, BitBoard blackBishops,
         BitBoard blackQueen, BitBoard blackKing, BitBoard whitePawns, BitBoard whiteRooks, BitBoard whiteKnights,
         BitBoard whiteBishops, BitBoard whiteQueen, BitBoard whiteKing){
        //Values in Centipawns
        //Black "Suit" locations
        this.blackPawns = blackPawns;
        this.blackRooks = blackRooks;
        this.blackKnights = blackKnights;
        this.blackBishops = blackBishops;
        this.blackQueens = blackQueen;
        this.blackKing = blackKing;
        //White "Suit" locations
        this.whitePawns = whitePawns;
        this.whiteRooks = whiteRooks;
        this.whiteKnights = whiteKnights;
        this.whiteBishops = whiteBishops;
        this.whiteQueens = whiteQueen;
        this.whiteKing = whiteKing;

        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

    }

    ChessGame(BoardInitializer ini){

        //Values in Centipawns
        //Black "Suit" locations
        this.blackPawns = ini.generateBitset("p", 100);
        this.blackRooks = ini.generateBitset("r", 500);
        this.blackKnights = ini.generateBitset("n", 300);
        this.blackBishops = ini.generateBitset("b", 301);
        this.blackQueens = ini.generateBitset("q", 900);
        this.blackKing = ini.generateBitset("k", 600000);
        //White "Suit" locations
        this.whitePawns = ini.generateBitset("P", -100);
        this.whiteRooks = ini.generateBitset("R", -500);
        this.whiteKnights = ini.generateBitset("N", -300);
        this.whiteBishops = ini.generateBitset("B", -301);
        this.whiteQueens = ini.generateBitset("Q", -900);
        this.whiteKing = ini.generateBitset("K", -600000);

        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

    }

    ChessGame(){
        this(new BoardInitializer());
    }

    void move(int indexFrom, int indexTo,BitSet boardPiece){
        this.blackBishops.clear(indexFrom);
        this.blackPawns.clear(indexFrom);
        this.blackKing.clear(indexFrom);
        this.blackRooks.clear(indexFrom);
        this.blackKnights.clear(indexFrom);
        this.blackQueens.clear(indexFrom);

        this.whiteBishops.clear(indexFrom);
        this.whitePawns.clear(indexFrom);
        this.whiteKing.clear(indexFrom);
        this.whiteQueens.clear(indexFrom);
        this.whiteKnights.clear(indexFrom);
        this.whiteRooks.clear(indexFrom);

        this.blackBishops.clear(indexTo);
        this.blackPawns.clear(indexTo);
        this.blackKing.clear(indexTo);
        this.blackRooks.clear(indexTo);
        this.blackKnights.clear(indexTo);
        this.blackQueens.clear(indexTo);

        this.whiteBishops.clear(indexTo);
        this.whitePawns.clear(indexTo);
        this.whiteKing.clear(indexTo);
        this.whiteQueens.clear(indexTo);
        this.whiteKnights.clear(indexTo);
        this.whiteRooks.clear(indexTo);

        boardPiece.set(indexTo);
        updateFullBoards();
    }


    public int finalMove(){

        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueens); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueens); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);

        int cutoff = Integer.MAX_VALUE;

        for(int i = whiteQueens.nextSetBit(0); i >= 0; i = whiteQueens.nextSetBit(i+1)){
            BitSet movements = queenMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.whiteQueens);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whitePawns.nextSetBit(0); i >= 0; i = whitePawns.nextSetBit(i+1)){
            BitSet movements = whitePawnMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whitePawns);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteBishops.nextSetBit(0); i >= 0; i = whiteBishops.nextSetBit(i+1)){
            BitSet movements = bishopMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteBishops);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteKnights.nextSetBit(0); i >= 0; i = whiteKnights.nextSetBit(i+1)){
            BitSet movements = knightMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteKnights);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteRooks.nextSetBit(0); i >= 0; i = whiteRooks.nextSetBit(i+1)){
            BitSet movements = rookMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteRooks);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }

        for(int i = whiteKing.nextSetBit(0); i >= 0; i = whiteKing.nextSetBit(i+1)){
            BitSet movements = kingMovement(i, fullWhite, fullBlack);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                        (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                        (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

                ChessGameCopy.move(i, j, ChessGameCopy.whiteKing);
                int boardValue = eval.totalBoardValue(ChessGameCopy.whitePawns, ChessGameCopy.whiteRooks, ChessGameCopy.whiteKnights,
                        ChessGameCopy.whiteBishops, ChessGameCopy.whiteQueens, ChessGameCopy.whiteKing, ChessGameCopy.blackPawns, ChessGameCopy.blackRooks,
                        ChessGameCopy.blackKnights, ChessGameCopy.blackBishops, ChessGameCopy.blackQueens, ChessGameCopy.blackKing);
                cutoff = Math.min(cutoff, boardValue);
            }
        }
        return cutoff;
    }


    public void simulate2ply(){
        BitSet fullBlack = new BitSet(64);
        BitSet fullWhite = new BitSet(64);
        //Full representation of black pieces
        fullBlack.or(blackPawns); fullBlack.or(blackRooks); fullBlack.or(blackKnights);
        fullBlack.or(blackQueens); fullBlack.or(blackKing); fullBlack.or(blackBishops);
        //Full representation of white pieces
        fullWhite.or(whitePawns); fullWhite.or(whiteRooks); fullWhite.or(whiteKnights);
        fullWhite.or(whiteQueens); fullWhite.or(whiteKing); fullWhite.or(whiteBishops);

        int cutoff = Integer.MIN_VALUE;
        Object[] move = new Object[3];

        for(int i = blackQueens.nextSetBit(0); i >= 0; i = blackQueens.nextSetBit(i+1)){
            BitSet movements = queenMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.whiteQueens);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackQueens;
                }
            }
        }

        for(int i = blackPawns.nextSetBit(0); i >= 0; i = blackPawns.nextSetBit(i+1)){
            BitSet movements = blackPawnMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackPawns);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackPawns;
                }
            }
        }

        for(int i = blackKnights.nextSetBit(0); i >= 0; i = blackKnights.nextSetBit(i+1)){
            BitSet movements = knightMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackKnights);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackKnights;
                }
            }
        }

        for(int i = blackBishops.nextSetBit(0); i >= 0; i = blackBishops.nextSetBit(i+1)){
            BitSet movements = bishopMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackBishops);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackBishops;
                }
            }
        }

        for(int i = blackRooks.nextSetBit(0); i >= 0; i = blackRooks.nextSetBit(i+1)){
            BitSet movements = rookMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)){
                ChessGame ChessGameCopy = copyThis();
                ChessGameCopy.move(i, j, ChessGameCopy.blackRooks);
                int boardValue = ChessGameCopy.finalMove();
                if(boardValue > cutoff){
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackRooks;
                }
            }
        }

        for(int i = blackKing.nextSetBit(0); i >= 0; i = blackKing.nextSetBit(i+1)) {
            BitSet movements = kingMovement(i, fullBlack, fullWhite);
            for (int j = movements.nextSetBit(0); j >= 0; j = movements.nextSetBit(j + 1)) {
                ChessGame ChessGameCopy = new ChessGame((BitBoard) blackPawns.clone(), (BitBoard) blackRooks.clone(), (BitBoard) blackKnights.clone(),
                        (BitBoard) blackBishops.clone(), (BitBoard) blackQueens.clone(), (BitBoard) blackKing.clone(), (BitBoard) whitePawns.clone(), (BitBoard) whiteRooks.clone(),
                        (BitBoard) whiteKnights.clone(), (BitBoard) whiteBishops.clone(), (BitBoard) whiteQueens.clone(), (BitBoard) whiteKing.clone());
                ChessGameCopy.move(i, j, ChessGameCopy.blackKing);
                int boardValue = ChessGameCopy.finalMove();
                if (boardValue > cutoff) {
                    cutoff = boardValue;
                    move[0] = i;
                    move[1] = j;
                    move[2] = blackKing;
                }
            }
        }

        this.move((int)move[0], (int)move[1],(BitBoard) move[2]);
        System.out.printf("From:%d To:%d\n", move[0], move[1]);
    }


    public Boolean legalMove(int iFrom, int iTo){
        if(whiteBoard.get(iFrom)){
            BitBoard tempBoard = pieceFinder(iFrom);
            if(legalMovement(iFrom, iTo, tempBoard)){
                if(!inCheck(iFrom, iTo)){
                    return true;
                }
            }
        }
        return false; //false if index is not a white piece, is not a legal move or leaves king in check
    }

    private Boolean inCheck(int iFrom, int iTo){
        ChessGame ChessGameCopy = new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());
        ChessGameCopy.move(iFrom, iTo, ChessGameCopy.pieceFinder(iFrom));
        BitSet allMovements = new BitSet(64);
        for(int i = ChessGameCopy.blackRooks.nextSetBit(0); i >= 0; i = ChessGameCopy.blackRooks.nextSetBit(i+1)){
            allMovements.or(rookMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
        }

        for(int i = ChessGameCopy.blackPawns.nextSetBit(0); i >= 0; i = ChessGameCopy.blackPawns.nextSetBit(i+1)){
            allMovements.or(blackPawnMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
        }

        for(int i = ChessGameCopy.blackBishops.nextSetBit(0); i >= 0; i = ChessGameCopy.blackBishops.nextSetBit(i+1)){
            allMovements.or(bishopMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
        }

        for(int i = ChessGameCopy.blackKnights.nextSetBit(0); i >= 0; i = ChessGameCopy.blackKnights.nextSetBit(i+1)){
            allMovements.or(knightMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
        }

        for(int i = ChessGameCopy.blackQueens.nextSetBit(0); i >= 0; i = ChessGameCopy.blackQueens.nextSetBit(i+1)){
            allMovements.or(queenMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
        }

        for(int i = ChessGameCopy.blackKing.nextSetBit(0); i >= 0; i = ChessGameCopy.blackKing.nextSetBit(i+1)){
            allMovements.or(kingMovement(i, ChessGameCopy.blackBoard, ChessGameCopy.whiteBoard));
        }

        return allMovements.get(ChessGameCopy.whiteKing.nextSetBit(0));

    }

    private Boolean checkMate(){
        return false;
    }

    BitBoard pieceFinder(int index){
        if(whitePawns.get(index)){
            return whitePawns;
        }

        if(whiteBishops.get(index)){
            return whiteBishops;
        }

        if(whiteRooks.get(index)){
            return whiteRooks;
        }

        if(whiteKnights.get(index)){
            return whiteKnights;
        }

        if(whiteQueens.get(index)){
            return whiteQueens;
        }

        return whiteKing;
    }

    BitBoard pieceFinderBlack(int index){
        if(blackPawns.get(index)){
            return blackPawns;
        }

        if(blackBishops.get(index)){
            return blackBishops;
        }

        if(blackRooks.get(index)){
            return blackRooks;
        }

        if(blackKnights.get(index)){
            return blackKnights;
        }

        if(blackQueens.get(index)){
            return blackQueens;
        }

        return blackKing;
    }

    private Boolean legalMovement(int iFrom, int iTo, BitBoard piece){
        BitSet movements;
        switch (piece.pieceValue()){
            case -100 : movements = whitePawnMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case -300 : movements = knightMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case -301 : movements = bishopMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case -500 : movements = rookMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            case -900 : movements = queenMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));

            default : movements = kingMovement(iFrom, whiteBoard, blackBoard);
                return (movements.get(iTo));
        }
    }

    private void updateFullBoards(){
        whiteBoard = new BitSet(64);
        blackBoard = new BitSet(64);

        whiteBoard.or(this.whitePawns); whiteBoard.or(this.whiteRooks); whiteBoard.or(this.whiteKnights);
        whiteBoard.or(this.whiteQueens); whiteBoard.or(this.whiteKing); whiteBoard.or(this.whiteBishops);

        blackBoard.or(this.blackPawns); blackBoard.or(this.blackRooks); blackBoard.or(this.blackKnights);
        blackBoard.or(this.blackQueens); blackBoard.or(this.blackKing); blackBoard.or(this.blackBishops);

    }

    public BitSet queenMovement(int queenIndex, BitSet allyBoard, BitSet enemyBoard) {
        BitSet movement = new BitSet(64);
        int index = queenIndex;
        if(index > -1) {
            this.sideways(index, movement, allyBoard, enemyBoard);
            this.diagonal(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    public BitSet rookMovement(int rookIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = rookIndex;
        if(index > -1) {
            this.sideways(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    public BitSet bishopMovement(int bishopIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = bishopIndex;
        if(index > -1) {
            this.diagonal(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    public BitSet knightMovement(int knightIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = knightIndex;
        if(index > -1) {
            if (index + 10 < 64 && index % 8 < (index + 10) % 8) {
                if(!allyBoard.get(index+10)) {
                    movement.set(index + 10);
                }
            }

            if (index - 10 > -1 && index % 8 > (index - 10) % 8) {
                if(!allyBoard.get(index - 10)) {
                    movement.set(index - 10);
                }
            }

            if (index + 17 < 64 && index % 8 < (index + 17) % 8) {
                if(!allyBoard.get(index + 17)) {
                    movement.set(index + 17);
                }
            }

            if (index - 17 > -1 && index % 8 > (index - 17) % 8) {
                if(!allyBoard.get(index - 17)) {
                    movement.set(index - 17);
                }
            }

            if (index + 15 < 64 && index % 8 > (index + 15) % 8) {
                if(!allyBoard.get(index + 15)) {
                    movement.set(index + 15);
                }
            }

            if (index - 15 > -1 && index % 8 < (index - 15) % 8) {
                if(!allyBoard.get(index - 15)) {
                    movement.set(index - 15);
                }
            }

            if (index + 6 < 64 && index % 8 > (index + 6) % 8) {
                if(!allyBoard.get(index + 6)) {
                    movement.set(index + 6);
                }
            }

            if (index - 6 > -1 && index % 8 < (index - 6) % 8) {
                if(!allyBoard.get(index - 6)) {
                    movement.set(index - 6);
                }
            }
        }
        return movement;
    }

    public BitSet kingMovement(int kingIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = kingIndex;

        if(index+1 < (index/8)*8 + 8 && !allyBoard.get(index+1)){
            movement.set(index+1);
        }

        if(index+8 < 64 && !allyBoard.get(index+8)){
            movement.set(index+8);
        }

        if(index+9 < 64 && index%8<(index+9)%8 && !allyBoard.get(index+9)){
            movement.set(index+9);
        }

        if(index+7 < 64 && index%8>(index+7)%8 && !allyBoard.get(index+7)){
            movement.set(index+7);
        }

        if(index-1 > (index/8)*8 -1 && !allyBoard.get(index-1)){
            movement.set(index-1);
        }

        if(index-8 > -1 && !allyBoard.get(index-8)){
            movement.set(index-8);
        }

        if(index-9 > -1 && index%8>(index-9)%8 && !allyBoard.get(index-9)){
            movement.set(index-9);
        }

        if(index-7 > -1 && index%8<(index-7)%8 && !allyBoard.get(index-7)){
            movement.set(index-7);
        }

        return movement;
    }

    public BitSet blackPawnMovement(int pawnIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = pawnIndex;
        if(index >- 1) {
            if(index+8 < 64 && !allyBoard.get(index+8) && !enemyBoard.get(index+8)){
                movement.set(index+8);
            }

            if(index+9 < 64 && index%8<(index+9)%8 && enemyBoard.get(index+9)){
                movement.set(index+9);
            }

            if(index+7 < 64 && index%8>(index+7)%8 && enemyBoard.get(index+7)){
                movement.set(index+7);
            }

            if(index+16 < 64 && !allyBoard.get(index+16) && !enemyBoard.get(index+16) && this.in(index, new int[]{8,9,10,11,12,13,14,15})){
                movement.set(index+16);
            }
        }
        return movement;
    }

    public BitSet whitePawnMovement(int pawnIndex, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = pawnIndex;
        if(index >- 1) {
            if(index-8 > -1 && !allyBoard.get(index-8) && !enemyBoard.get(index-8)){
                movement.set(index-8);
            }

            if(index-9 > -1 && index%8>(index-9)%8 && enemyBoard.get(index-9)){
                movement.set(index-9);
            }

            if(index-7 > -1 && index%8<(index-7)%8 && enemyBoard.get(index-7)){
                movement.set(index-7);
            }
            if(index-16 > -1 && !allyBoard.get(index-8) && !enemyBoard.get(index-8) && this.in(index, new int[]{48,49,50,51,52,53,54,55})){
                movement.set(index-16);
            }
        }
        return movement;
    }

    private void diagonal(int index, BitSet movement, BitSet allyBoard, BitSet enemyBoard){

        //Down-Right
        for (int i = index + 9; i < 64; i = i + 9) {
            if( i%8 == 0) { break;}else{
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }

        //Down-Left
        for (int i = index + 7; i < 64; i = i + 7) {
            if (index%8 < i%8) {break;}else{
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }

        //Up-Left
        for (int i = index - 9; i > 0; i = i - 9) {
            if (index%8 < i%8) {break;}else {
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }

        //Up-Right
        for (int i = index - 7; i > 0; i = i - 7) {
            if(i%8 == 0){break;}else {
                if (allyBoard.get(i)) {
                    break;
                } else {
                    if (enemyBoard.get(i)) {
                        movement.set(i);
                        break;
                    } else {
                        movement.set(i);
                    }
                }
            }
        }
    }

    private void sideways(int index, BitSet movement, BitSet allyBoard, BitSet enemyBoard){
        //Down
        for (int i = index + 8; i < 64; i = i + 8) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }

        //Right
        for (int i = index + 1; i < (index/8)*8 + 8; ++i) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }

        //Up
        for (int i = index - 8; i > -1; i = i - 8) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }

        //Left
        for (int i = index - 1; i > (index/8)*8 -1; i = i - 1) {
            if (allyBoard.get(i)) {
                break;
            } else {
                if (enemyBoard.get(i)) {
                    movement.set(i);
                    break;
                } else {
                    movement.set(i);
                }
            }
        }
    }

    private boolean in (int i, int[] ugh){
        for(int u : ugh){
            if(i == u){
                return true;
            }
        }
        return false;
    }

    public ChessGame copyThis(){
        return new ChessGame((BitBoard)blackPawns.clone(),  (BitBoard)blackRooks.clone(),  (BitBoard)blackKnights.clone(),
                (BitBoard)blackBishops.clone(), (BitBoard)blackQueens.clone(), (BitBoard)blackKing.clone(),  (BitBoard)whitePawns.clone(),  (BitBoard)whiteRooks.clone(),
                (BitBoard)whiteKnights.clone(),(BitBoard)whiteBishops.clone(), (BitBoard)whiteQueens.clone(),  (BitBoard)whiteKing.clone());

    }
}
