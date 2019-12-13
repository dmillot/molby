using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class UserGroup
    {
        [JsonProperty("is_leader")]
        public int IsLeader { get; set; }
        [JsonProperty("nb_points_awarded")]
        public int NbPointsAwarded { get; set; }
        public Group Group { get; set; }
    }
}
