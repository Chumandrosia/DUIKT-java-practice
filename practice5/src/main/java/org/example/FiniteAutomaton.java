package org.example;

public class FiniteAutomaton {

    public enum State {
        S,
        ONE,
        TWO,
        THREE,
        F
    }

    private State currentState;

    public FiniteAutomaton() {
        this.currentState = State.S;
    }

    public State process(String input) {
        currentState = State.S;

        if (input == null) {
            return currentState;
        }

        for (char c : input.toCharArray()) {
            transition(c);

            if (currentState == State.F) {
                return currentState;
            }
        }

        return currentState;
    }

    private void transition(char c) {
        switch (currentState) {
            case S:
                if (c == 'T') {
                    currentState = State.ONE;
                }
                break;

            case ONE:
                if (c == 'E') {
                    currentState = State.TWO;
                } else if (c == 'T') {

                    currentState = State.ONE;
                } else {
                    currentState = State.S;
                }
                break;

            case TWO:
                if (c == 'S') {
                    currentState = State.THREE;
                } else if (c == 'T') {
                    currentState = State.ONE;
                } else {
                    currentState = State.S;
                }
                break;

            case THREE:
                if (c == 'T') {
                    currentState = State.F;
                } else {
                    currentState = State.S;
                }
                break;

            case F:
                break;
        }
    }

    public State getCurrentState() {
        return currentState;
    }
}