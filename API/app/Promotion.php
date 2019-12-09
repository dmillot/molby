<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Promotion extends Model
{
    protected $table = "year_group";
    public $timestamps = false;

    public function developers()
    {
        return $this->hasMany('App\Developer', 'id_year_group');
    }
}
