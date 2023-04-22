import sys
import numpy as np
import networkx as nx
import random
import pandas as pd

# CHANGE ################################
influencers = [0, 348, 414, 3980, 686]
# CHANGE ################################

p = 0.01
chic_choc_path = 'chic_choc_data.csv'
cost_path = 'costs.csv'


def create_graph(edges_path: str) -> nx.Graph:
    """
    Creates the Chic Choc social network
    :param edges_path: A csv file that contains information obout "friendships" in the network
    :return: The Chic Choc social network
    """
    edges = pd.read_csv(edges_path).to_numpy()
    net = nx.Graph()
    net.add_edges_from(edges)
    return net


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


def buy_products(net: nx.Graph, purchased: set) -> set:
    """
    Gets the network at the beginning of stage t and simulates a purchase round
    :param net: The network at stage t
    :param purchased: All the users who bought a doll up to and including stage t-1
    :return: All the users who bought a doll up to and including stage t
    """
    new_purchases = set()
    for user in net.nodes:
        neighborhood = set(net.neighbors(user))
        b = len(neighborhood.intersection(purchased))
        n = len(neighborhood)
        prob = b / n
        if prob >= random.uniform(0, 1):
            new_purchases.add(user)

    return new_purchases.union(purchased)


def get_influencers_cost(cost_path: str, influencers: list) -> int:
    """
    Returns the cost of the influencers you chose
    :param cost_path: A csv file containing the information about the costs
    :param influencers: A list of your influencers
    :return: Sum of costs
    """
    costs = pd.read_csv(cost_path)
    return sum([costs[costs['user'] == influencer]['cost'].item()
                if influencer in list(costs['user'])
                else 0 for influencer in influencers])


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
    set.to_csv("C:\\Users\\USER\\Desktop\\hw1_ec\\addition" + str(outer) + ".csv", index=False)

