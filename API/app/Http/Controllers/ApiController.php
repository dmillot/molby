<?php

namespace App\Http\Controllers;

use App\Reward;
use App\Level;
use App\Group;
use App\Promotion;
use App\Developer;
use App\UserReward;
use App\UserGroup;
use App\UserLevel;
use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
use Illuminate\Http\Request;

class ApiController extends Controller
{
    public function rewards()
    {
        $rewards =  Reward::with('developers.developer', 'level')->get();
        // we use return "return response()->json($developers);" instead of "$rewards->toJson()"
        // because json() method automatically set headers
        return response()->json($rewards);
    }

    public function reward($id)
    {
        $reward = Reward::with('developers.developer', 'level')->find($id);
        return response()->json($reward);
    }

    public function redeemreward(Request $request, $id)
    {
        // get the user id
        $user_id = $request->get("id_user");

        // if missing parameter, return error
        if ($user_id == null) {
            $error = [
                "type" => "MissingParameterException",
                "message" => "missing parameter",
                "code" => 501
            ];

            return response()->json($error);
        }

        $reward = Reward::find($id);
        $developer = Developer::find($user_id);

        // if doesn't find developer or reward, return error
        if ($developer == null || $reward == null) {
            $error = [
                "type" => "DataException",
                "message" => "no data",
                "code" => 800
            ];

            return response()->json($error);
        }

        if ($developer->xp < $reward->cost_xp) {
            $error = [
                "type" => "InsufficientXP",
                "message" => "the developer does not have enough experience",
                "code" => 900
            ];

            return response()->json($error);
        }

        $reward_user = UserReward::where([
            ['id_user', '=', $developer->id],
            ['id_reward', '=', $reward->id]
        ])->first();

        if ($reward_user == null) {
            $response = UserReward::create([
                'id_user' => $developer->id,
                'id_reward' => $reward->id,
                'date_asked' => Carbon::now()->toDateTimeString()
            ]);
        } else {
            $error = [
                "type" => "DuplicateEntry",
                "message" => "the developer has already redeemed this reward",
                "code" => 901
            ];

            return response()->json($error);
        }

        return response()->json($response);
    }

    public function levels()
    {
        $levels =  Level::with('developers.developer', 'rewards')->get();
        return response()->json($levels);
    }

    public function level($id)
    {
        $level = Level::with('developers.developer', 'rewards')->find($id);
        return response()->json($level);
    }

    public function test()
    {
        // get the user id
        $user_id = 1;

        $level = Level::find(3);
        $developer = Developer::find($user_id);

        $levels = Level::orderBy('required_xp', 'asc')->select('id', 'required_xp')->get();

        function objectToArray($object)
        {
            return json_decode(json_encode($object), true);
        }

        // var_dump(objectToArray($levels));
        // die;

        $currentLevel = UserLevel::with('level')->where('id_user', "=", 1)->get();
        dd($currentLevel);

        $key = array_search('50000', array_column(objectToArray($levels), 'required_xp'));
        var_dump($key);
        die;

        if ($developer->xp < ($level->required_xp + $level->cost_xp)) {
            $error = [
                "type" => "InvalidParameter",
                "message" => "the developer does not have enough experience",
                "code" => 900
            ];

            return response()->json($error);
        }

        if ($developer->xp < ($level->required_xp + $level->cost_xp)) {
            $error = [
                "type" => "InvalidParameter",
                "message" => "the developer does not have enough experience",
                "code" => 900
            ];

            return response()->json($error);
        }

        $level_user = UserLevel::where([
            ['id_user', '=', $developer->id],
            ['id_level', '=', $level->id]
        ])->first();

        if ($level_user == null) {
            $response = UserLevel::create([
                'id_user' => $developer->id,
                'id_level' => $level->id,
                'date_obtained' => Carbon::now()->toDateTimeString()
            ]);
        } else {
            $error = [
                "type" => "DuplicateEntry",
                "message" => "the developer already has this level",
                "code" => 901
            ];

            return response()->json($error);
        }

        return response()->json($response);
    }

    public function unlock(Request $request, $id)
    {
        // get the user id
        $user_id = $request->get("id_user");

        // if missing parameter, return error
        if ($user_id == null) {
            $error = [
                "type" => "MissingParameterException",
                "message" => "missing parameter",
                "code" => 501
            ];

            return response()->json($error);
        }

        $level = Level::find($id);
        $developer = Developer::find($user_id);

        // if doesn't find developer or reward, return error
        if ($developer == null || $level == null) {
            $error = [
                "type" => "DataException",
                "message" => "no data",
                "code" => 800
            ];

            return response()->json($error);
        }

        if ($developer->xp < ($level->required_xp + $level->cost_xp)) {
            $error = [
                "type" => "InvalidParameter",
                "message" => "the developer does not have enough experience",
                "code" => 900
            ];

            return response()->json($error);
        }

        $level_user = UserLevel::where([
            ['id_user', '=', $developer->id],
            ['id_level', '=', $level->id]
        ])->first();

        if ($level_user == null) {
            $response = UserLevel::create([
                'id_user' => $developer->id,
                'id_level' => $level->id,
                'date_obtained' => Carbon::now()->toDateTimeString()
            ]);
        } else {
            $error = [
                "type" => "DuplicateEntry",
                "message" => "the developer already has this level",
                "code" => 901
            ];

            return response()->json($error);
        }

        return response()->json($response);
    }

    public function groups()
    {
        $groups =  Group::all();
        return response()->json($groups);
    }

    public function group($id)
    {
        $group = Group::find($id);
        return response()->json($group);
    }

    public function points(Request $request, $id)
    {
        
        // get the user id
        $user_id = $request->get("id_user");
        // get points amount
        $points = $request->get("points");
        // get the member to assign points
        $member_id = $request->get("id_member");

        // if missing parameter, return error
        if ($user_id == null || $points == null || $member_id == null) {
            $error = [
                "type" => "MissingParameterException",
                "message" => "missing parameter",
                "code" => 501
            ];

            return response()->json($error);
        }

        $group = Group::find($id);
        $developer = Developer::with('group')->find($user_id);
        $member = Developer::find($member_id);

        // if doesn't find developer or reward, return error
        if ($developer == null || $member == null || $group == null) {
            $error = [
                "type" => "DataException",
                "message" => "no data",
                "code" => 800
            ];

            return response()->json($error);
        }

        if ($group->available_points_to_give < $points)
        {
            $error = [
                "type" => "InvalidParameter",
                "message" => "the group does not have enough points",
                "code" => 900
            ];

            return response()->json($error);
        }

        if ($developer->group->is_leader == 0)
        {
            $error = [
                "type" => "InvalidParameter",
                "message" => "the developer is not a leader",
                "code" => 900
            ];

            return response()->json($error);
        }

        $group_user = UserGroup::where([
            ['id_user', '=', $developer->id],
            ['id_group', '=', $group->id]
        ])->first();

        $group_member = UserGroup::where([
            ['id_user', '=', $member->id],
            ['id_group', '=', $group->id]
        ])->first();

        if ($group_user != null && $group_member != null) {
            $response = $group_user->update([
                'nb_points_awarded' => $points
            ]);
        } else {
            $error = [
                "type" => "DataException",
                "message" => "no data",
                "code" => 800
            ];

            return response()->json($error);
        }

        if ($response)
        {
            return response()->json($group_user);
        } else {
            $error = [
                "type" => "DataException",
                "message" => "no data",
                "code" => 800
            ];

            return response()->json($error);
        }
        
    }

    public function promotions()
    {
        $promotions =  Promotion::with('developers')->get();
        return response()->json($promotions);
    }

    public function promotion($id)
    {
        $promotion = Promotion::with('developers')->find($id);
        return response()->json($promotion);
    }

    public function developers()
    {
        $developers =  Developer::with('promotion', 'group.group', 'levels.level', 'rewards.reward')->get();
        return response()->json($developers);
    }

    public function developer($id)
    {
        $developer = Developer::with('promotion', 'group.group', 'levels.level', 'rewards.reward')->find($id);
        return response()->json($developer);
    }

    public function create(Request $request)
    {
        $reward = new Reward();
        $reward->label = $request->get("label");
        $reward->cost_xp = 100;
        $reward->nb_available = 100;
        $reward->id_level = 1;
        $reward->skin = "ghjklm";

        $reward->description = "fhgjklm";
        $reward->save();
        return $reward->toJson();
    }
}
