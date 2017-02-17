package com.media2359.mediacorpspellinggame.game;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;

import com.media2359.mediacorpspellinggame.R;
import com.media2359.mediacorpspellinggame.base.BaseActivity;
import com.media2359.mediacorpspellinggame.data.Game;
import com.media2359.mediacorpspellinggame.data.Question;
import com.media2359.mediacorpspellinggame.factory.GameProgressManager;
import com.media2359.mediacorpspellinggame.factory.GameRepo;
import com.media2359.mediacorpspellinggame.game.typeA.SingleQuestionFragment;
import com.media2359.mediacorpspellinggame.game.typeB.GridQuestionsFragment;
import com.media2359.mediacorpspellinggame.utils.ActivityUtils;

import butterknife.ButterKnife;

/**
 * Created by xijunli on 15/2/17.
 */

public class GameActivity extends BaseActivity {

    private static final String EXTRA_GAME = "extra_game";

    @NonNull
    private Game currentGame;

    private int sectionScore;

    private int sectionTime;

    public static void startGameActivity(Activity activity, @NonNull Game game){
        Intent intent = new Intent(activity, GameActivity.class);
        intent.putExtra(EXTRA_GAME, game);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        currentGame = getIntent().getParcelableExtra(EXTRA_GAME);

        GameProgressManager.getInstance().setLastAttemptedGamePos(currentGame.getGameId());
        GameProgressManager.getInstance().setLastAttemptedQuestionPos(-1);

        GameIntroFragment gameIntroFragment = GameIntroFragment.newInstance(currentGame);
        ActivityUtils.addFragmentToActivity(getFragmentManager(), gameIntroFragment, R.id.container, false, false);
    }

    private void replaceFragment(Fragment fragment) {
        ActivityUtils.replaceFragmentInActivity(getFragmentManager(), fragment, R.id.container, false, true);
    }

    public void showNextMultipleQuestionFragment() {
        if (GameProgressManager.getInstance().getLastAttemptedQuestionPos() < 0){
            replaceFragment(GridQuestionsFragment.newInstance(currentGame.getGameId()));
            GameProgressManager.getInstance().setLastAttemptedQuestionPos(currentGame.getQuestionCount());
        }else {
            startNextGame();
        }

    }

    public void showNextSingleQuestionFragment() {

        int nextQuestionPos = getNextQuestionPosition();

        if (nextQuestionPos < 0) {
            // if this game has finished
            startNextGame();
            return;
        }

        Question question = GameRepo.getInstance().getQuestion(currentGame.getQuestionIdList()[nextQuestionPos]);
        replaceFragment(SingleQuestionFragment.newInstance(question));

        GameProgressManager.getInstance().setLastAttemptedQuestionPos(nextQuestionPos);
    }

    private int getNextQuestionPosition() {

        int lastAttemptedQuestionPos = GameProgressManager.getInstance().getLastAttemptedQuestionPos();

        int nextQuestionPos;

        // check if this is the first question of the game
        if (lastAttemptedQuestionPos < 0) {

            nextQuestionPos = 0;
            GameProgressManager.getInstance().setLastAttemptedGamePos(currentGame.getGameId());

            // if the last attempted question position is same as size-1, then should go to next game
            // e.g Total Questions count: 4, last attempted question position: 3
        } else if (lastAttemptedQuestionPos < currentGame.getQuestionCount() - 1){
            // if this game hasn't finished
            nextQuestionPos = lastAttemptedQuestionPos + 1;

        }else {
            // if this game has finished
            nextQuestionPos = -1;
        }

        return nextQuestionPos;
    }

    public void startNextGame() {
        int lastAttemptedGameId = GameProgressManager.getInstance().getLastAttemptedGamePos();

        if (lastAttemptedGameId == currentGame.getGameId()){

            Game nextGame = GameRepo.getInstance().getGame(lastAttemptedGameId + 1);

            if (nextGame == null){
                Intent intent = new Intent(this, SummaryActivity.class);
                startActivity(intent);
                finish();
            }else {
                GameActivity.startGameActivity(this, nextGame);
            }
        }else {
            throw new IllegalArgumentException("last attempted game id: " + lastAttemptedGameId + " is not the same as current game id: " + currentGame.getGameId());
        }
    }

    @NonNull
    public Game getCurrentGame() {
        return currentGame;
    }
}
