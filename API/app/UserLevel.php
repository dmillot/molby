<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class UserLevel extends Model
{
    protected $table = "link_user_level";
    public $timestamps = false;

    protected $hidden = [
        'id_user', 'id_level'
    ];

    public function developer()
    {
        return $this->belongsTo('App\Developer', 'id_user');
    }

    public function level()
    {
        return $this->belongsTo('App\Level', 'id_level');
    }
}
