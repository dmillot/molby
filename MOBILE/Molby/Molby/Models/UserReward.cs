using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class UserReward
    {
        [JsonProperty("date_validation")]
        public DateTime? DateValidation { get; set; }
        [JsonProperty("date_used")]
        public DateTime? DateUsed { get; set; }
        [JsonProperty("date_asked")]
        public DateTime? DateAsked { get; set; }
        public Reward Reward { get; set; }
    }
}