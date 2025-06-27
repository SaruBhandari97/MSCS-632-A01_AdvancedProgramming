% Basic parent relationships
parent(prakash, saru).
parent(kamala, saru).
parent(prakash, prajwal).
parent(kamala, prajwal).
parent(janak, anjana).
parent(gita, anjana).
parent(janak, suman).
parent(gita, suman).
parent(janak, roshni).
parent(gita, roshni).
parent(janak, abhishek).
parent(gita, abhishek).
parent(prakash, nabeena).
parent(kamala, nabeena).
parent(prakash, sanjay).
parent(kamala, sanjay).
parent(prakash, pradeepti).
parent(kamala, pradeepti).
parent(prakash, prajjan).
parent(kamala, prajjan).
parent(saru, aarav).
parent(prajjwal, aarav).
parent(anjana, diwas).
parent(suman, diwas).
parent(roshni, kavya).
parent(abhishek, kavya).
parent(roshni, nishant).
parent(abhishek, nishant).
parent(nabeena, meera).
parent(sanjay, meera).
parent(pradeepti, ritu).
parent(prajjan, ritu).

male(prakash).
male(janak).
male(prajjwal).
male(suman).
male(abhishek).
male(sanjay).
male(prajjan).
male(aarav).
male(diwas).
male(nishant).

female(kamala).
female(gita).
female(saru).
female(anjana).
female(roshni).
female(nabeena).
female(pradeepti).
female(meera).
female(kavya).
female(ritu).

grandparent(X, Y) :-
    parent(X, Z),
    parent(Z, Y).

sibling(X, Y) :-
    parent(P, X),
    parent(P, Y),
    X \= Y.

cousin(X, Y) :-
    parent(PX, X),
    parent(PY, Y),
    sibling(PX, PY),
    X \= Y.

child(X, Y) :-
    parent(Y, X).

descendant(X, Y) :-
    parent(Y, X).
descendant(X, Y) :-
    parent(Z, X),
    descendant(Z, Y).
