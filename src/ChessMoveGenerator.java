import java.util.BitSet;

/**
 * Created by Tyrael on 4/29/2017.
 */
public class ChessMoveGenerator{

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


}

