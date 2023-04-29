import pandas as pd
def createSet (set, group, indexList):
    while(True):
        permutationList = []
        for index in indexList:
            permutationList.append(group[index])
        set.append(permutationList)
        if (indexList[-1] == len(group) - 1):
            innerIndex = len(indexList) - 1
            existsMargin = False
            while (innerIndex != 0 and (not existsMargin)):
                if (indexList[innerIndex - 1] < indexList[innerIndex] - 1):
                    existsMargin = True
                innerIndex -= 1
            if (not existsMargin):
                return set
            indexList[innerIndex] += 1
            innerIndex += 1
            while (innerIndex < len(indexList)):
                indexList[innerIndex] = indexList[innerIndex - 1] + 1
                innerIndex += 1
        else:
            indexList[-1] += 1


def permutationSet(group,size):
    return(createSet([],group,[i for i in range(size)]))

def createInfluencerCsv(group,size):
    groupSet=permutationSet(group,size)

    csvSet = pd.DataFrame(groupSet, columns=["influencer "+str(i+1) for i in range(5)])
    csvSet.to_csv("C:\\Users\\USER\\Desktop\\hw1_ec\\permutations2.csv", index=False)

if __name__=='__main__':
     createInfluencerCsv([1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16],5)