
package com.onecricket.APICallingPackage.retrofit.livescore;

import java.util.List;
import com.google.gson.annotations.Expose;

@SuppressWarnings("unused")
public class Secondinnnings {

    @Expose
    private List<Batsman> batsmen;
    @Expose
    private Bowler bowler;
    @Expose
    private Long inningNumber;
    @Expose
    private OverallScores overallScores;
    @Expose
    private String score;
    @Expose
    private String team;
    @Expose
    private String wickets;

    public List<Batsman> getBatsmen() {
        return batsmen;
    }

    public void setBatsmen(List<Batsman> batsmen) {
        this.batsmen = batsmen;
    }

    public Bowler getBowler() {
        return bowler;
    }

    public void setBowler(Bowler bowler) {
        this.bowler = bowler;
    }

    public Long getInningNumber() {
        return inningNumber;
    }

    public void setInningNumber(Long inningNumber) {
        this.inningNumber = inningNumber;
    }

    public OverallScores getOverallScores() {
        return overallScores;
    }

    public void setOverallScores(OverallScores overallScores) {
        this.overallScores = overallScores;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getWickets() {
        return wickets;
    }

    public void setWickets(String wickets) {
        this.wickets = wickets;
    }

}
