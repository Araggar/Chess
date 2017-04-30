import java.util.BitSet;

/**
 * Created by Tyrael on 4/29/2017.
 */
public class ChessMoveGenerator implements MoveGenerator {
    int[] fuckingCol = new int[]{7, 15, 23, 31, 39, 47, 55, 63};

    public void moveTo(int Index) {

    }

    public BitSet queenMovement(BitSet queen, BitSet allyBoard, BitSet enemyBoard) {
        BitSet movement = new BitSet(64);
        int index = queen.nextSetBit(0);
        if(index > -1) {
            this.sideways(index, movement, allyBoard, enemyBoard);
            this.diagonal(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    public BitSet rookMovement(BitSet rook, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = rook.nextSetBit(0);
        if(index > -1) {
            this.sideways(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    public BitSet bishopMovement(BitSet bishop, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = bishop.nextSetBit(0);
        if(index > -1) {
            this.diagonal(index, movement, allyBoard, enemyBoard);
        }
        return movement;
    }

    public BitSet knightMovement(BitSet knight, BitSet allyBoard, BitSet enemyBoard){
        BitSet movement = new BitSet(64);
        int index = knight.nextSetBit(0);
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

    public BitSet kingMovement(BitSet knight, BitSet allyBoard, BitSet enemyBoard){
        return knight;
    }

    public  BitSet pawnMovement(BitSet knight, BitSet allyBoard, BitSet enemyBoard){
        return knight;
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





}

