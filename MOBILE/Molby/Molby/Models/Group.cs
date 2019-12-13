using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class Group
    {
        public int Id { get; set; }
        public string Name { get; set; }
        [JsonProperty("available_points")]
        public string AvailablePoints { get; set; }
        [JsonProperty("available_points_to_give")]
        public string AvailablePointsToGive { get; set; }
        [JsonProperty("total_xp")]
        public string TotalXp { get; set; }
        [JsonProperty("date_start")]
        public DateTime DateStart { get; set; }
        [JsonProperty("date_end")]
        public DateTime DateEnd { get; set; }
        [JsonProperty("users")]
        public List<GroupMember> Members { get; set; }
        public string Duration
        {
            get
            {
                return this.DateStart.ToString("dd/MM/yyyy") + " - " + this.DateEnd.ToString("dd/MM/yyyy");
            }
        }

        public string Xp
        {
            get
            {
                return this.TotalXp + " XP";
            }
        }
    }
}
