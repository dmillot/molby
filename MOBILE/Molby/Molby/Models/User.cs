using Newtonsoft.Json;
using System;
using System.Collections.Generic;
using System.Text;

namespace Molby.Models
{
    public class User
    {
        private string _picture;
        public int Id { get; set; }
        public string Name { get; set; }
        public string Firstname { get; set; }
        public string Email { get; set; }
        public string Picture { get => _picture; set
            {
                //_picture = "http://10.5.0.43/public/" + value;
                _picture = value;
            }
        }
        public int Xp { get; set; }
        [JsonProperty("promotion")]
        public YearGroup YearGroup { get; set; }
        public UserGroup Group { get; set; }
        public List<UserLevel> Levels { get; set; }
        public List<UserReward> Rewards { get; set; }
        public string Fullname
        {
            get {
                return char.ToUpper(this.Firstname[0]) + this.Firstname.ToLower().Substring(1) + " " + this.Name.ToUpper();
            }
        }

        public string FormatXp
        {
            get
            {
                return this.Xp + " XP";
            }
        }
    }
}
