using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class GroupMember
    {
        public int IsLeader { get; set; }
        [JsonProperty("nb_points_awarded")]
        public int NbPointsAwarded { get; set; }
        [JsonProperty("developer")]
        public User Member { get; set; }
    }
}
