:- dynamic symptom/1.

% Diagnosis rules
diagnosis('flu') :-
    symptom(fever),
    symptom(cough),
    symptom(body_ache).

diagnosis('cold') :-
    symptom(sneezing),
    symptom(sore_throat),
    symptom(headache).

diagnosis('malaria') :-
    symptom(fever),
    symptom(chills),
    symptom(sweating).

diagnosis('food_poisoning') :-
    symptom(abdominal_pain),
    symptom(fatigue),
    symptom(loss_of_appetite).

% Default
diagnosis('unknown').

