package cl.ucn.disc.dsm.fcaimanque.ataqnaval;

import cl.ucn.disc.dsm.fcaimanque.ataqnaval.adapters.TableroAdapter;
import cl.ucn.disc.dsm.fcaimanque.ataqnaval.model.Celda;

/**
 * Valida el despliegue de las naves dependiendo del tipo
 */
public class DespliegueNaves {
    private Celda checkL1;
    private Celda checkL2;
    private Celda checkL3;
    private Celda checkL4;
    private Celda checkL5;
    private Celda checkM1;
    private Celda checkM2;
    private Celda checkM3;


    //valida colocacion horizontal
    public boolean checkArrangeLH(TableroAdapter board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount() - 4; i++) {
            Celda cell = board.getItem(i);
            if (cell.getStatus() == Celda.Status.OCUPADO) {
                if (((i + 1) % boardSize == boardSize - 1) ||
                        ((i + 2) % boardSize == boardSize - 1) ||
                        ((i + 3) % boardSize == boardSize - 1)) {
                } else {
                    Celda cell2 = board.getItem(i + 1);
                    if (cell2.getStatus() == Celda.Status.OCUPADO) {
                        Celda cell3 = board.getItem(i + 2);
                        if (cell3.getStatus() == Celda.Status.OCUPADO) {
                            Celda cell4 = board.getItem(i + 3);
                            if (cell4.getStatus() == Celda.Status.OCUPADO) {
                                Celda cell5 = board.getItem(i + 4);
                                if (cell5.getStatus() == Celda.Status.OCUPADO) {
                                    checkL1 = board.getItem(i);
                                    checkL2 = board.getItem(i + 1);
                                    checkL3 = board.getItem(i + 2);
                                    checkL4 = board.getItem(i + 3);
                                    checkL5 = board.getItem(i + 4);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //valida colocacion vertical
    public boolean checkArrangeLV(TableroAdapter board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < (board.getCount() - (boardSize * 4)); i++) {
            Celda cell = board.getItem(i);
            if (cell.getStatus() == Celda.Status.OCUPADO) {
                Celda cell2 = board.getItem(i + boardSize);
                if (cell2.getStatus() == Celda.Status.OCUPADO) {
                    Celda cell3 = board.getItem(i + boardSize * 2);
                    if (cell3.getStatus() == Celda.Status.OCUPADO) {
                        Celda cell4 = board.getItem(i + boardSize * 3);
                        if (cell4.getStatus() == Celda.Status.OCUPADO) {
                            Celda cell5 = board.getItem(i + boardSize * 4);
                            if (cell5.getStatus() == Celda.Status.OCUPADO) {
                                checkL1 = board.getItem(i);
                                checkL2 = board.getItem(i + boardSize);
                                checkL3 = board.getItem(i + boardSize * 2);
                                checkL4 = board.getItem(i + boardSize * 3);
                                checkL5 = board.getItem(i + boardSize * 4);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean checkArrangeMH(TableroAdapter board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount() - 2; i++) {
            Celda cell = board.getItem(i);
            if (cell.getStatus() == Celda.Status.OCUPADO) {
                if ((board.getItem(i) != checkL1) && (board.getItem(i) != checkL2) && (board.getItem(i) != checkL3) && (board.getItem(i) != checkL4) && (board.getItem(i) != checkL5)) {
                    if (((i + 1) % boardSize == boardSize - 1) ||
                            ((i + 2) % boardSize == boardSize - 1)) {
                    } else {
                        Celda cell2 = board.getItem(i + 1);
                        if (cell2.getStatus() == Celda.Status.OCUPADO) {
                            Celda cell3 = board.getItem(i + 2);
                            if (cell3.getStatus() == Celda.Status.OCUPADO) {
                                if ((cell3 != checkL1) && (cell3 != checkL2) && (cell3 != checkL3)
                                        && (cell3 != checkL4) && (cell3 != checkL5)) {
                                    checkM1 = board.getItem(i);
                                    checkM2 = board.getItem(i + 1);
                                    checkM3 = board.getItem(i + 2);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean checkArrangeMV(TableroAdapter board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < (board.getCount() - (boardSize * 2)); i++) {
            Celda cell = board.getItem(i);
            if (cell.getStatus() == Celda.Status.OCUPADO) {
                if ((board.getItem(i) != checkL1) && (board.getItem(i) != checkL2) && (board.getItem(i) != checkL3) && (board.getItem(i) != checkL4) && (board.getItem(i) != checkL5)) {
                    Celda cell2 = board.getItem(i + boardSize);
                    if (cell2.getStatus() == Celda.Status.OCUPADO) {
                        Celda cell3 = board.getItem(i + boardSize * 2);
                        if (cell3.getStatus() == Celda.Status.OCUPADO) {
                            if ((cell3 != checkL1) && (cell3 != checkL2) && (cell3 != checkL3)
                                    && (cell3 != checkL4) && (cell3 != checkL5)) {
                                checkM1 = board.getItem(i);
                                checkM2 = board.getItem(i + boardSize);
                                checkM3 = board.getItem(i + boardSize * 2);
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean checkArrangeSH(TableroAdapter board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < board.getCount(); i++) {
            Celda cell = board.getItem(i);
            if (cell.getStatus() == Celda.Status.OCUPADO) {
                if ((board.getItem(i) != checkM1) && (board.getItem(i) != checkM2) && (board.getItem(i) != checkM3)
                        && ((board.getItem(i) != checkL1) && (board.getItem(i) != checkL2) && (board.getItem(i) != checkL3) && (board.getItem(i) != checkL4) && (board.getItem(i) != checkL5))) {
                    if ((i + 1) % boardSize == boardSize - 1) {
                    } else {
                        Celda cell2 = board.getItem(i + 1);
                        if (cell2.getStatus() == Celda.Status.OCUPADO) {
                            if ((cell2 != checkM1) && (cell2 != checkM2) && (cell2 != checkM3)
                                    && (cell2 != checkL1) && (cell2 != checkL2) && (cell2 != checkL3)
                                    && (cell2 != checkL4) && (cell2 != checkL5)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    public boolean checkArrangeSV(TableroAdapter board) {
        int boardSize = (int) Math.sqrt(board.getCount());
        for (int i = 0; i < (board.getCount() - boardSize); i++) {
            Celda cell = board.getItem(i);
            if (cell.getStatus() == Celda.Status.OCUPADO) {
                if ((board.getItem(i) != checkM1) && (board.getItem(i) != checkM2) && (board.getItem(i) != checkM3)
                        && ((board.getItem(i) != checkL1) && (board.getItem(i) != checkL2) && (board.getItem(i) != checkL3) && (board.getItem(i) != checkL4) && (board.getItem(i) != checkL5))) {
                    Celda cell2 = board.getItem(i + boardSize);
                    if (cell2.getStatus() == Celda.Status.OCUPADO) {
                        if ((cell2 != checkM1) && (cell2 != checkM2) && (cell2 != checkM3)
                                && (cell2 != checkL1) && (cell2 != checkL2) && (cell2 != checkL3)
                                && (cell2 != checkL4) && (cell2 != checkL5)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
