import sys
import numpy as np
import networkx as nx
import random
import pandas as pd

p = 0.01
chic_choc_path = 'chic_choc_data.csv'
cost_path = 'costs.csv'

def change_network(net: nx.Graph) -> nx.Graph:
    """
    Gets the network at staged t and returns the network at stage t+1 (stochastic)
    :param net: The network at staged t
    :return: The network at stage t+1
    """
    edges_to_add = []
    for user1 in sorted(net.nodes):
        for user2 in sorted(net.nodes, reverse=True):
            if user1 == user2:
                break
            if (user1, user2) not in net.edges:
                neighborhood_size = len(set(net.neighbors(user1)).intersection(set(net.neighbors(user2))))
                prob = 1 - ((1 - p) ** (np.log(neighborhood_size))) if neighborhood_size > 0 else 0  # #################
                if prob >= random.uniform(0, 1):
                    edges_to_add.append((user1, user2))
    net.add_edges_from(edges_to_add)
    return net,edges_to_add


if __name__ == '__main__':

    edges = pd.read_csv(chic_choc_path).to_numpy()
    outer=sys.argv[1]

    print("STARTING " + str(outer) + " OUTER ROUND")
    csvData = []
    chic_choc_network = nx.Graph()
    chic_choc_network.add_edges_from(edges.copy())

    for i in range(6):
        csvData.append(("user " + str(i + 1), "friend " + str(i + 1)))
        chic_choc_network, edges_to_add = change_network(chic_choc_network)
        csvData.extend(edges_to_add)
        print("finished round", i + 1)
    set = pd.DataFrame(csvData, columns=['user', 'firend'])
    set.to_csv("C:\\Users\\USER\\Desktop\\hw1_ec\\additions\\addition" + str(outer) + ".csv", index=False)

