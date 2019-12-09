<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserReward extends Model
{
    protected $table = "link_reward_user";
    public $timestamps = false;

    protected $hidden = [
        'id_user', 'id_reward'
    ];

    protected $fillable = ['id_user', 'id_reward', 'date_asked'];

    public function developer()
    {
        return $this->belongsTo('App\Developer', 'id_user');
    }

    public function reward()
    {
        return $this->belongsTo('App\Reward', 'id_reward');
    }
}
