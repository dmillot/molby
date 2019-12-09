<?php

namespace App;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Database\Eloquent\Builder;

class UserGroup extends Model
{
    protected $table = "link_user_group";
    public $timestamps = false;
    
    protected function setKeysForSaveQuery(Builder $query)
    {
        $query
            ->where('id_user', '=', $this->getAttribute('id_user'))
            ->where('id_group', '=', $this->getAttribute('id_group'));
        return $query;
    }

    protected $hidden = [
        'id_user', 'id_group'
    ];

    protected $fillable = [
        'nb_points_awarded'
    ];

    public function developer()
    {
        return $this->belongsTo('App\Developer', 'id_user');
    }

    public function group()
    {
        return $this->belongsTo('App\Group', 'id_group');
    }
}
