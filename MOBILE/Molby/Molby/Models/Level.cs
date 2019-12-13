using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class Level
    {
        private string _skin;
        public int Id { get; set; }
        public string Label { get; set; }
        public string Description { get; set; }
        [JsonProperty("cost_xp")]
        public int CostXp { get; set; }
        [JsonProperty("required_xp")]
        public int RequiredXp { get; set; }
        public string Skin { get => _skin; set
            {
                //_skin = "http://10.5.0.43/public/" + value;
                _skin = value;
            }
        }
        [JsonProperty("developers")]
        public List<User> Users { get; set; }
        public List<Reward> Rewards { get; set; }

    }
}
