def checkUnique(vals):
    boolArray = [False for _ in range(10)]
    for val in vals:
        if val != '.':
            if boolArray[int(val)] is True: # if we've seen this value before, it's a duplicate
                return False
            else:
               boolArray[int(val)] = True 
               # the first time we see this value, set the corresponding bool to True
    return True

def rowValid(board, row, col):
    vals = board[row]
    return checkUnique(vals)

def colValid(board, row, col):
    vals = [board[i][col] for i in range(9)] 
    return checkUnique(vals)

def blockValid(board, row, col):
    upper_row = 3*(row/3)
    left_col = 3*(col/3)
    vals = []
    for i in range(3):
        vals += board[upper_row+i][left_col: left_col+3]
    return checkUnique(vals)

def isValid(board, row, col):
    #returns True if value board[row][col] does not also appear in its row, col or 3x3 block
    if rowValid(board, row, col) and colValid(board, row, col) and blockValid(board, row, col):
        return True
    else:
        return False


def pos2rowCol(pos):
    # returns the row and column  corresponding to pos
    return pos/9, pos%9


def solve_sudoku(board, pos):
    if pos == 81:
        return True
    row, col = pos2rowCol(pos)
    if board[row][col] != '.': 
        # this position is already filled by default, so move to next one
        return solve_sudoku(board, pos+1)

    else:
        for i in range(1,10):
            board[row][col] = str(i)
            if isValid(board, row, col):
                # The value we just filled in is valid, so move to next blank box
                if solve_sudoku(board, pos+1):
                    return True
        board[row][col] = '.'
        return False



board = [
[1,2,3,4,5,6,7,8,9],
[4,5,6,7,8,9,1,2,3],
[7,8,9,1,2,3,4,5,3],
['.','.','.','.','.','.','.','.','.'],
['.','.','.','.','.','.','.','.','.'],
['.','.','.','.','.','.','.','.','.'],
['.','.','.','.','.','.','.','.','.'],
['.','.','.','.','.','.','.','.','.'],
['.','.','.','.','.','.','.','.','.']
]

board = [
['.','.','.',1,'.',5,'.',6,8],
['.','.','.','.','.','.',7,'.',1],
[9,'.',1,'.','.','.','.',3,'.'],
['.','.',7,'.',2,6,'.','.','.'],
[5,'.','.','.','.','.','.','.',3],
['.','.','.',8,7,'.',4,'.','.'],
['.',3,'.','.','.','.',8,'.',5],
[1,'.',5,'.','.','.','.','.','.'],
[7,9,'.',4,'.',1,'.','.','.']
]

print isValid(board, 0, 8)

solve_sudoku(board,0)

print board
