using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class Reward
    {
        public int Id { get; set; }
        public string Description { get; set; }
        public string Label { get; set; }
        [JsonProperty("cost_xp")]
        public int CostXp { get; set; }
        [JsonProperty("nb_available")]
        public int NbAvailable { get; set; }
        public string Skin { get; set; }
        public Level Level { get; set; }
        [JsonProperty("developers")]
        public List<User> Users { get; set; }
    }
}
