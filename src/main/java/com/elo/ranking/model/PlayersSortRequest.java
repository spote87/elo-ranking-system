package com.elo.ranking.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Shivaji Pote
 */
@Setter
@Getter
public class PlayersSortRequest {

    private String sortBy;

    private String order;
}
